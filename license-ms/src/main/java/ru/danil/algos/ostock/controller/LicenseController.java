package ru.danil.algos.ostock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.danil.algos.ostock.model.License;
import ru.danil.algos.ostock.service.LicenseService;

import java.util.Locale;

@RestController
@RequestMapping("v1/organization/{organizationId}/license")
@RequiredArgsConstructor
public class LicenseController {
    private final LicenseService licenseService;

    @GetMapping("/{licenseId}")
    public ResponseEntity<License> getLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId
    ) {
        return new ResponseEntity<>(licenseService.getLicense(licenseId, organizationId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createLicense(
            @PathVariable String organizationId,
            @RequestBody License request,
            @RequestHeader(value = "Accept-Language", required = false)
            Locale locale
    ) {
        System.out.println(locale);
        return new ResponseEntity<>(licenseService.createLicense(request, organizationId, locale), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> updateLicense(
            @PathVariable String organizationId,
            @RequestBody License license
    ) {
        return new ResponseEntity<>(licenseService.updateLicense(license, organizationId), HttpStatus.OK);
    }

    @DeleteMapping("/{licenseId}")
    public ResponseEntity<String> deleteLicense(
            @PathVariable String organizationId,
            @PathVariable String licenseId
    ) {
        return new ResponseEntity<>(licenseService.deleteLicense(licenseId, organizationId), HttpStatus.OK);
    }
}
