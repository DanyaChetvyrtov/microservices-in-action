package ru.danil.algos.ostock.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.danil.algos.ostock.config.Props;
import ru.danil.algos.ostock.model.License;
import ru.danil.algos.ostock.model.Organization;
import ru.danil.algos.ostock.repository.LicenseRepository;
import ru.danil.algos.ostock.service.client.OrganizationFeignClient;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
@RequiredArgsConstructor
public class LicenseService {
    private final MessageSource messageSource;
    private final LicenseRepository licenseRepository;
    private final OrganizationFeignClient organizationFeignClient;
    private final RedisService redisService;
    private final Props props;

    public License getLicense(String organizationId, String licenseId, boolean withOrganization) {
        var organizationUUID = UUID.fromString(organizationId);
        var licenseUUID = UUID.fromString(licenseId);

        var license = licenseRepository.findByOrganizationIdAndLicenseId(organizationUUID, licenseUUID)
                .orElseThrow(() -> new IllegalArgumentException(convertedResponse(organizationId, licenseId)));

        if (!withOrganization) return license;

//        Organization organization = retrieveOrganizationInfo(organizationId);
        Organization organization = redisService.getValue(organizationId);
        if (organization == null) {
            log.debug("Organization with id {} was not found in redis", organizationId);
            organization = retrieveOrganizationInfo(organizationId);
            if (organization != null) {
                log.debug("Organization with id {} was not found in organization-ms", organizationId);
                organization.setId(organizationId);
                redisService.setValue(organization);
            }
            log.debug("Organization with id {} was successfully received from organization-ms", organizationId);
        }

        if (organization != null) addOrganizationFields(license, organization);

        return license.withComment(props.getProperty());
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
                messageSource.getMessage("Deleting license with id %s", null, null),
                licenseId
        );
    }

    private Organization retrieveOrganizationInfo(String organizationId) {
        return organizationFeignClient.getOrganization(organizationId);
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

    private String convertedResponse(String organizationId, String licenseId) {
        return String.format(
                messageSource.getMessage("license.search.error.message", null, null),
                organizationId, licenseId
        );
    }

    private void addOrganizationFields(License license, Organization organization) {
        license.setOrganizationName(organization.getName());
        license.setContactName(organization.getContactName());
        license.setContactEmail(organization.getContactEmail());
        license.setContactPhone(organization.getContactPhone());
    }

}
