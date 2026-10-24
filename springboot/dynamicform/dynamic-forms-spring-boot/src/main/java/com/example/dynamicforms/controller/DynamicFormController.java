package com.example.dynamicforms.controller;

import com.example.dynamicforms.dto.*;
import com.example.dynamicforms.service.DynamicFormService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DynamicFormController {
    private final DynamicFormService service;

    public DynamicFormController(DynamicFormService service) {
        this.service = service;
    }

    @GetMapping("/forms/{code}")
    public FormDto getForm(@PathVariable String code) {
        return service.getForm(code);
    }

    @PostMapping("/forms/{code}/submissions")
    public SubmissionDto submit(@PathVariable String code, @RequestBody SubmissionRequest request) {
        return service.submit(code, request);
    }

    @GetMapping("/submissions/{id}")
    public SubmissionDto getSubmission(@PathVariable Long id) {
        return service.getSubmission(id);
    }

    @GetMapping("/forms/{code}/submissions")
    public List<SubmissionDto> listSubmissions(@PathVariable String code) {
        return service.listSubmissions(code);
    }
}
