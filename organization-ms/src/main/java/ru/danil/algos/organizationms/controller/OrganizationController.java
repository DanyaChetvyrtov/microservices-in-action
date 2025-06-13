package ru.danil.algos.organizationms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.danil.algos.organizationms.mode.Organization;
import ru.danil.algos.organizationms.service.OrganizationService;

@RestController
@RequestMapping("v1/organization")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @GetMapping("/{organizationId}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable("organizationId") String organizationId) {
        return new ResponseEntity<>(organizationService.getById(organizationId), HttpStatus.OK);
    }

    @PutMapping(value="/{organizationId}")
    public ResponseEntity<Organization> updateOrganization(@PathVariable("organizationId") String id, @RequestBody Organization organization) {
        return ResponseEntity.ok(organizationService.update(organization));
    }

    @PostMapping
    public ResponseEntity<Organization>  saveOrganization(@RequestBody Organization organization) {
        System.out.println(organization);
        return ResponseEntity.ok(organizationService.create(organization));
    }

    @RequestMapping(value="/{organizationId}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization( @PathVariable String organizationId) {
        organizationService.delete(organizationId);
    }
}
