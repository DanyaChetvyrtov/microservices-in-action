package ru.danil.algos.organizationms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.danil.algos.organizationms.mode.Organization;
import ru.danil.algos.organizationms.repository.OrganizationRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public Organization getById(String organizationId) {
        return organizationRepository.findById(UUID.fromString(organizationId))
                .orElseThrow(() -> new RuntimeException("Organization not found"));
    }

    public Organization create(Organization organization) {
        organization.setOrganizationId(UUID.randomUUID());
        return organizationRepository.save(organization);
    }

    public Organization update(Organization organization) {
        var existedOrganization = getById(organization.getOrganizationId().toString());

        existedOrganization.setName(organization.getName());
        existedOrganization.setContactName(organization.getContactName());
        existedOrganization.setContactEmail(organization.getContactEmail());
        existedOrganization.setContactPhone(organization.getContactPhone());

        return organizationRepository.save(organization);
    }

    public void delete(String organizationId) {
        organizationRepository.deleteById(UUID.fromString(organizationId));
    }
}
