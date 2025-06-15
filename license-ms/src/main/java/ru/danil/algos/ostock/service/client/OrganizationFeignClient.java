package ru.danil.algos.ostock.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.danil.algos.ostock.model.Organization;

@FeignClient("organization-ms")
public interface OrganizationFeignClient {
    @GetMapping(
            value="/v1/organization/{organizationId}",
            consumes="application/json")
    Organization getOrganization(@PathVariable("organizationId") String organizationId);
}
