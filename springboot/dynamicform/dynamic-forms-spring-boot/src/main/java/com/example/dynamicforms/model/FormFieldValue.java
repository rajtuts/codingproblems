package com.example.dynamicforms.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "form_field_values", uniqueConstraints =
        @UniqueConstraint(name = "uk_submission_field", columnNames = {"submission_id", "field_key"}))
@Getter @Setter
public class FormFieldValue {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "submission_id")
    private FormSubmission submission;

    @Column(name = "field_key", nullable = false)
    private String fieldKey;

    // JSON value: string, number, boolean, etc.
    @Column(nullable = false, length = 10000)
    private String valueJson;
}
