package ru.danil.algos.ostock.service;

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

import java.util.UUID;

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
        return licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId)
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

    public License getLicense(String licenseId, String organizationId, String clientType){
        License license = licenseRepository
                .findByOrganizationIdAndLicenseId(organizationId, UUID.fromString(licenseId))
                .orElseThrow(IllegalArgumentException::new);

        if (null == license) {
            throw new IllegalArgumentException(String.format(messageSource.getMessage("license.search.error.message", null, null),licenseId, organizationId));
        }

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
}
