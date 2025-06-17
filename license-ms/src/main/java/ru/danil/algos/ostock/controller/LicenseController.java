package ru.danil.algos.ostock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.danil.algos.ostock.model.License;
import ru.danil.algos.ostock.service.LicenseService;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("v1/organization/{organizationId}/license")
@RequiredArgsConstructor
public class LicenseController {
    private final LicenseService licenseService;

    @GetMapping("/{licenseId}")
    public ResponseEntity<License> getLicensesWithClient(
            @PathVariable String organizationId,
            @PathVariable String licenseId,
            @RequestParam(name = "extended", required = false, defaultValue = "false") boolean withOrganization) {
        return new ResponseEntity<>(licenseService.getLicense(organizationId, licenseId, withOrganization), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<License>> getLicenses(@PathVariable String organizationId) {
        return new ResponseEntity<>(licenseService.getLicensesByOrganizationId(UUID.fromString(organizationId)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<License> createLicense(
            @RequestBody License request,
            @RequestHeader(value = "Accept-Language", required = false)
            Locale locale
    ) {
        System.out.println(locale);
        return new ResponseEntity<>(licenseService.createLicense(request), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<License> updateLicense(
            @PathVariable String organizationId,
            @RequestBody License license
    ) {
        return new ResponseEntity<>(licenseService.updateLicense(license), HttpStatus.OK);
    }

    @DeleteMapping("/{licenseId}")
    public ResponseEntity<String> deleteLicense(
            @PathVariable String organizationId,
            @PathVariable String licenseId
    ) {
        return new ResponseEntity<>(licenseService.deleteLicense(licenseId), HttpStatus.OK);
    }
}
