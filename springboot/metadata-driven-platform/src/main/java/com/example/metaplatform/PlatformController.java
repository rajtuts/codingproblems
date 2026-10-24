package com.example.metaplatform;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api")
public class PlatformController {
    private final MetadataService service;

    public PlatformController(MetadataService service) { this.service = service; }

    // Demo only: in production, derive tenant ID from a validated JWT / authenticated principal.
    private String tenant(HttpServletRequest request) {
        String tenant = request.getHeader("X-Tenant-Id");
        if (tenant == null || !tenant.matches("[A-Za-z0-9_-]{1,100}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Provide a valid X-Tenant-Id header (demo tenant selector only)");
        }
        return tenant;
    }

    @PostMapping("/definitions/{name}")
    public Map<String, Object> createDefinition(@PathVariable String name,
        @RequestBody JsonNode schema, HttpServletRequest request) {
        try {
            MetadataEntity entity = service.createDefinition(tenant(request), name, schema);
            return Map.of("id", entity.getId(), "tenantId", entity.getTenantId(),
                "name", entity.getName(), "schema", schema, "createdAt", entity.getCreatedAt().toString());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/definitions/{name}")
    public JsonNode getDefinition(@PathVariable String name, HttpServletRequest request) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().readTree(
                service.getDefinition(tenant(request), name).getSchemaJson());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to read schema");
        }
    }

    @PostMapping("/data/{entityName}")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> createRecord(@PathVariable String entityName,
        @RequestBody JsonNode data, HttpServletRequest request) {
        try {
            RecordEntity record = service.createRecord(tenant(request), entityName, data);
            return Map.of("id", record.getId(), "tenantId", record.getTenantId(),
                "entityName", record.getEntityName(), "data", data, "createdAt", record.getCreatedAt().toString());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/data/{entityName}")
    public List<Map<String, Object>> listRecords(@PathVariable String entityName,
        HttpServletRequest request) {
        try {
            return service.listRecords(tenant(request), entityName).stream().map(r -> {
                try {
                    return Map.<String, Object>of("id", r.getId(), "tenantId", r.getTenantId(),
                        "entityName", r.getEntityName(),
                        "data", new com.fasterxml.jackson.databind.ObjectMapper().readTree(r.getDataJson()),
                        "createdAt", r.getCreatedAt().toString());
                } catch (Exception e) { throw new IllegalStateException(e); }
            }).toList();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
