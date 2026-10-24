package com.example.metaplatform;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "entity_definition", uniqueConstraints =
    @UniqueConstraint(name = "uk_definition_tenant_name", columnNames = {"tenant_id", "name"}))
public class MetadataEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenant_id", nullable = false, length = 100)
    private String tenantId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, columnDefinition = "CLOB")
    private String schemaJson;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    protected MetadataEntity() {}

    public MetadataEntity(String tenantId, String name, String schemaJson) {
        this.tenantId = tenantId;
        this.name = name;
        this.schemaJson = schemaJson;
    }

    public Long getId() { return id; }
    public String getTenantId() { return tenantId; }
    public String getName() { return name; }
    public String getSchemaJson() { return schemaJson; }
    public Instant getCreatedAt() { return createdAt; }
}
