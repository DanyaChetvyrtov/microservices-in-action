package ru.danil.algos.organizationms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danil.algos.organizationms.mode.Organization;
import ru.danil.algos.organizationms.repository.OrganizationRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public Organization getById(String organizationId) {
        return organizationRepository.findById(UUID.fromString(organizationId))
                .orElseThrow(() -> new RuntimeException("Organization not found"));
    }

    @Transactional
    public Organization create(Organization organization) {
        System.out.println(organization);
//        organization.setOrganizationId(UUID.randomUUID());
        System.out.println(organization);
        return organizationRepository.save(organization);
    }

    @Transactional
    public Organization update(Organization organization) {
        var existedOrganization = getById(organization.getOrganizationId().toString());

        existedOrganization.setName(organization.getName());
        existedOrganization.setContactName(organization.getContactName());
        existedOrganization.setContactEmail(organization.getContactEmail());
        existedOrganization.setContactPhone(organization.getContactPhone());

        return organizationRepository.save(organization);
    }

    @Transactional
    public void delete(String organizationId) {
        organizationRepository.deleteById(UUID.fromString(organizationId));
    }
}
