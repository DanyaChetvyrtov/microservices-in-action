package ru.danil.algos.ostock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.danil.algos.ostock.config.Props;
import ru.danil.algos.ostock.model.License;
import ru.danil.algos.ostock.repository.LicenseRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LicenseService {
    private final MessageSource messageSource;
    private final LicenseRepository licenseRepository;
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
        license.setLicenseId(UUID.randomUUID());
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
}
