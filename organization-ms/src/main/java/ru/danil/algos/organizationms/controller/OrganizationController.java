package ru.danil.algos.organizationms.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.danil.algos.organizationms.mode.Organization;
import ru.danil.algos.organizationms.service.OrganizationService;

@RestController
@RequestMapping("v1/organization")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @GetMapping("/{organizationId}")
    @RolesAllowed({"ADMIN", "USER"})
//    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable("organizationId") String organizationId, Authentication authentication) {
        System.out.println("Authorities: " + authentication.getAuthorities());
        return new ResponseEntity<>(organizationService.getById(organizationId), HttpStatus.OK);
    }

    @PutMapping(value = "/{organizationId}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<Organization> updateOrganization(@PathVariable("organizationId") String id, @RequestBody Organization organization) {
        return ResponseEntity.ok(organizationService.update(organization));
    }

    @PostMapping
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<Organization> saveOrganization(@RequestBody Organization organization) {
        System.out.println(organization);
        return ResponseEntity.ok(organizationService.create(organization));
    }

    @DeleteMapping(value = "/{organizationId}")
    @RolesAllowed("ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable String organizationId) {
        organizationService.delete(organizationId);
    }
}
