package com.example.metaplatform;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "dynamic_record", indexes = {
    @Index(name = "idx_record_tenant_entity", columnList = "tenant_id, entity_name")
})
public class RecordEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenant_id", nullable = false, length = 100)
    private String tenantId;

    @Column(name = "entity_name", nullable = false, length = 100)
    private String entityName;

    @Column(nullable = false, columnDefinition = "CLOB")
    private String dataJson;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    protected RecordEntity() {}

    public RecordEntity(String tenantId, String entityName, String dataJson) {
        this.tenantId = tenantId;
        this.entityName = entityName;
        this.dataJson = dataJson;
    }

    public Long getId() { return id; }
    public String getTenantId() { return tenantId; }
    public String getEntityName() { return entityName; }
    public String getDataJson() { return dataJson; }
    public Instant getCreatedAt() { return createdAt; }
}
