package com.example.dynamicforms.repository;

import com.example.dynamicforms.model.FormSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FormSubmissionRepository extends JpaRepository<FormSubmission, Long> {
    List<FormSubmission> findByForm_CodeOrderBySubmittedAtDesc(String code);
}
