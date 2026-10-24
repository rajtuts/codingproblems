package com.example.dynamicforms.repository;

import com.example.dynamicforms.model.FormDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FormDefinitionRepository extends JpaRepository<FormDefinition, Long> {
    Optional<FormDefinition> findByCode(String code);
}
