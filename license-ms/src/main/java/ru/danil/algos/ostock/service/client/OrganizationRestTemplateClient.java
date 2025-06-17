package ru.danil.algos.ostock.service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.danil.algos.ostock.model.Organization;

@Component
@RequiredArgsConstructor
public class OrganizationRestTemplateClient {
    private final RestTemplate restTemplate;

    public Organization getOrganization(String organizationId) {
        ResponseEntity<Organization> restExchange = restTemplate.exchange(
//                "http://organization-ms/v1/organization/{organizationId}",
                "http://gateway-server:8072/organization/v1/organization/{organizationId}",
                HttpMethod.GET, null, Organization.class, organizationId
        );
        return restExchange.getBody();
    }
}
