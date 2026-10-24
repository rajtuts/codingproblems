package com.example.metaplatform;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MetadataRepository extends JpaRepository<MetadataEntity, Long> {
    Optional<MetadataEntity> findByTenantIdAndName(String tenantId, String name);
}
