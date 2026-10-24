package com.example.dynamicforms.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "form_fields", uniqueConstraints =
        @UniqueConstraint(name = "uk_form_field_key", columnNames = {"form_id", "field_key"}))
@Getter @Setter
public class FormField {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "form_id")
    private FormDefinition form;

    @Column(name = "field_key", nullable = false)
    private String fieldKey;

    @Column(nullable = false)
    private String label;

    // TEXT, NUMBER, EMAIL, TEXTAREA, DROPDOWN, RADIO, CHECKBOX, DATE
    @Column(nullable = false)
    private String type;

    private boolean required;
    private boolean readOnly;

    // JSON array, e.g. ["Engineering","Finance"]
    @Column(length = 4000)
    private String optionsJson;

    // Used as the authoritative display/submission value for read-only fields
    private String defaultValue;

    private int sortOrder;
}
