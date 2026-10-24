package com.example.jwtdemo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthFlowTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    @Test
    void loginRefreshRotationAndReuseDetection() throws Exception {
        // 1. No token -> 401
        mvc.perform(get("/api/me")).andExpect(status().isUnauthorized());

        // 2. Login
        JsonNode tokens = json(postJson("/api/auth/login", Map.of("username", "user", "password", "user12345"))
                .andExpect(status().isOk()));
        String access = tokens.get("accessToken").asText();
        String refresh1 = tokens.get("refreshToken").asText();

        // 3. Access token works; admin endpoint is forbidden for a normal user
        mvc.perform(get("/api/me").header("Authorization", "Bearer " + access))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user"));
        mvc.perform(get("/api/admin/dashboard").header("Authorization", "Bearer " + access))
                .andExpect(status().isForbidden());

        // 4. Refresh -> new pair, old refresh token is now revoked
        JsonNode rotated = json(postJson("/api/auth/refresh", Map.of("refreshToken", refresh1))
                .andExpect(status().isOk()));
        String refresh2 = rotated.get("refreshToken").asText();

        // 5. Re-using the old token is rejected AND kills the whole token family
        postJson("/api/auth/refresh", Map.of("refreshToken", refresh1)).andExpect(status().isUnauthorized());
        postJson("/api/auth/refresh", Map.of("refreshToken", refresh2)).andExpect(status().isUnauthorized());
    }

    @Test
    void adminCanReachAdminEndpoint() throws Exception {
        JsonNode tokens = json(postJson("/api/auth/login", Map.of("username", "admin", "password", "admin12345"))
                .andExpect(status().isOk()));
        mvc.perform(get("/api/admin/dashboard")
                        .header("Authorization", "Bearer " + tokens.get("accessToken").asText()))
                .andExpect(status().isOk());
    }

    @Test
    void wrongPasswordIs401() throws Exception {
        postJson("/api/auth/login", Map.of("username", "user", "password", "nope-nope"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void tamperedTokenIs401() throws Exception {
        mvc.perform(get("/api/me").header("Authorization", "Bearer not.a.jwt"))
                .andExpect(status().isUnauthorized());
    }

    private ResultActions postJson(String url, Object body) throws Exception {
        return mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(body)));
    }

    private JsonNode json(ResultActions result) throws Exception {
        return om.readTree(result.andReturn().getResponse().getContentAsString());
    }
}
