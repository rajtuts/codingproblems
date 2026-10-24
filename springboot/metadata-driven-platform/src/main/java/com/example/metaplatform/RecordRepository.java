package com.example.metaplatform;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecordRepository extends JpaRepository<RecordEntity, Long> {
    List<RecordEntity> findTop100ByTenantIdAndEntityNameOrderByIdDesc(String tenantId, String entityName);
}
