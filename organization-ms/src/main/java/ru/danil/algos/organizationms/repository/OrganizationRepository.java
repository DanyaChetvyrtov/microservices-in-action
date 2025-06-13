package ru.danil.algos.organizationms.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.danil.algos.organizationms.mode.Organization;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, UUID> {
    @Override
    Optional<Organization> findById(UUID uuid);
}
