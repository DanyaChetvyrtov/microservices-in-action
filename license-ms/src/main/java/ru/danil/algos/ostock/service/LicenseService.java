package ru.danil.algos.ostock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.danil.algos.ostock.model.License;

import java.util.Locale;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LicenseService {
    private final MessageSource messageSource;

    public License getLicense(String licenseId, String organizationId) {
        return License.builder()
                .id(new Random().nextInt(100))
                .licenseId(licenseId)
                .organizationId(organizationId)
                .productName("Ostock")
                .description("Software product")
                .licenseType("full")
                .build();
    }

    public String createLicense(License license, String organizationId, Locale locale) {
        String responseMessage = null;
        if (license != null) {
            license.setOrganizationId(organizationId);
            responseMessage = String.format(
                    messageSource.getMessage(
                            "license.create.message", null, locale
                    ), license
            );
        }
        return responseMessage;
    }

    public String updateLicense(License license, String organizationId) {
        String responseMessage = null;
        if (license != null) {
            license.setOrganizationId(organizationId);
            responseMessage = String.format(
                    messageSource.getMessage(
                            "license.create.message", null, null
                    ), license
            );
        }
        return responseMessage;
    }

    public String deleteLicense(String licenseId, String organizationId) {
        return String.format(
                "Deleting license with id %s for the organization %s", licenseId, organizationId
        );
    }
}
