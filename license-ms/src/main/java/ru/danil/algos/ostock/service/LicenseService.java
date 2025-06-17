package ru.danil.algos.ostock.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.danil.algos.ostock.config.Props;
import ru.danil.algos.ostock.model.License;
import ru.danil.algos.ostock.model.Organization;
import ru.danil.algos.ostock.repository.LicenseRepository;
import ru.danil.algos.ostock.service.client.OrganizationDiscoveryClient;
import ru.danil.algos.ostock.service.client.OrganizationFeignClient;
import ru.danil.algos.ostock.service.client.OrganizationRestTemplateClient;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class LicenseService {
    private final MessageSource messageSource;
    private final LicenseRepository licenseRepository;
    private final OrganizationDiscoveryClient organizationDiscoveryClient;
    private final OrganizationRestTemplateClient organizationRestClient;
    private final OrganizationFeignClient organizationFeignClient;
    private final Props props;

    public License getLicense(UUID licenseId, String organizationId) {
        return licenseRepository.findByOrganizationIdAndLicenseId(UUID.fromString(organizationId), licenseId)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format(
                                messageSource.getMessage(
                                        "license.search.error.message", null, null)
                                , organizationId, licenseId)
                )).withComment(props.getProperty());
    }

    public License createLicense(License license) {
        licenseRepository.save(license);
        return license.withComment(props.getProperty());
    }

    public License updateLicense(License license) {
        licenseRepository.save(license);
        return license.withComment(props.getProperty());
    }

    public String deleteLicense(String licenseId) {
        licenseRepository.deleteById(UUID.fromString(licenseId));
        return String.format(
                messageSource.getMessage("Deleting license with id %s for the organization %s", null, null),
                licenseId
        );
    }

    public License getLicense(String organizationId, String licenseId, String clientType) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(UUID.fromString(organizationId), UUID.fromString(licenseId))
                .orElseThrow(() -> new IllegalArgumentException("Something went wrong"));

        if (null == license)
            throw new IllegalArgumentException(String.format(messageSource.getMessage("license.search.error.message", null, null), licenseId, organizationId));

        Organization organization = retrieveOrganizationInfo(organizationId, clientType);
        if (null != organization) {
            license.setOrganizationName(organization.getName());
            license.setContactName(organization.getContactName());
            license.setContactEmail(organization.getContactEmail());
            license.setContactPhone(organization.getContactPhone());
        }

        return license.withComment(props.getProperty());
    }

    private Organization retrieveOrganizationInfo(String organizationId, String clientType) {
        return switch (clientType) {
            case "feign" -> {
                System.out.println("I am using the feign client");
                yield organizationFeignClient.getOrganization(organizationId);
            }
            case "rest" -> {
                System.out.println("I am using the rest client");
                yield organizationRestClient.getOrganization(organizationId);
            }
            case "discovery" -> {
                System.out.println("I am using the discovery client");
                yield organizationDiscoveryClient.getOrganization(organizationId);
            }
            default -> organizationRestClient.getOrganization(organizationId);
        };
    }

    @CircuitBreaker(name = "licenseBreaker", fallbackMethod = "buildFallbackLicenseList")
    @Bulkhead(name = "bulkheadLicenseService", fallbackMethod = "buildFallbackLicenseList")
    @Retry(name = "retryLicenseService", fallbackMethod = "buildFallbackLicenseList")
    @RateLimiter(name = "ratelimiter", fallbackMethod = "buildFallbackLicenseList")
    public List<License> getLicensesByOrganizationId(UUID organizationId) {
//        sleep();
//        randomlyRunLong();
        return licenseRepository.findByOrganizationId(organizationId);
    }

    // for circuitBreaker test
    private void randomlyRunLong() {
        var rand = new Random();
        int randomNum = rand.nextInt(3) + 1;
        if (randomNum == 3) sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
            throw new TimeoutException();
        } catch (InterruptedException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    private List<License> buildFallbackLicenseList(UUID organizationId, Throwable t) {
        System.out.println("Fallback triggered due to: " + t.getMessage());
        return Collections.singletonList(
                License.builder()
                        .organizationId(organizationId)
                        .productName("Sorry no licensing information currently available")
                        .build());
    }
}
