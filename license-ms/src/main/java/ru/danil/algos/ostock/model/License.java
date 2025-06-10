package ru.danil.algos.ostock.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class License {
    private int id;
    private String licenseId;
    private String description;
    private String organizationId;
    private String productName;
    private String licenseType;
}
