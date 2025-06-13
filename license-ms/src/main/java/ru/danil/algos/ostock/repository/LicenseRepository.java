package ru.danil.algos.ostock.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.danil.algos.ostock.model.License;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LicenseRepository extends CrudRepository<License, UUID> {
    List<License> findByOrganizationId(UUID organizationId);
    Optional<License> findByOrganizationIdAndLicenseId(UUID organizationId, UUID licenseId);
}
