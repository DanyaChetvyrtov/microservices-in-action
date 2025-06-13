package ru.danil.algos.organizationms.mode;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "organization", schema = "organization_schema")
@NoArgsConstructor
@AllArgsConstructor
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Изменили генерацию
    @Column(name = "organization_id", columnDefinition = "UUID", nullable = false)
    private UUID organizationId;  // Теперь тип UUID (не String и не Long)

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "contact_name", nullable = false)
    private String contactName;

    @Column(name = "contact_email", nullable = false)
    private String contactEmail;

    @Column(name = "contact_phone", nullable = false)
    private String contactPhone;
}
