package com.ads.restaurante;

import com.ads.restaurante.dto.auth.LoginRequest;
import com.ads.restaurante.dto.prato.PratoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Percurso HTTP: cardápio público, rota de manager barrada sem token, login e acesso com token. */
@SpringBootTest
class AuthFlowTest {

    @Autowired
    WebApplicationContext context;

    final ObjectMapper json = new ObjectMapper();
    MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    void cardapio_publico_manager_protegido_login_libera() throws Exception {
        mvc.perform(get("/pratos")).andExpect(status().isOk());

        mvc.perform(post("/pratos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(new PratoRequest("X", new BigDecimal("1.00"), true))))
                .andExpect(status().isUnauthorized());

        MvcResult login = mvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(new LoginRequest("admin", "test-admin-12345"))))
                .andExpect(status().isOk())
                .andReturn();
        String token = json.readTree(login.getResponse().getContentAsString()).get("token").asText();
        assertThat(token).isNotBlank();

        mvc.perform(post("/pratos")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(new PratoRequest("Feijoada", new BigDecimal("45.00"), true))))
                .andExpect(status().isCreated());
    }

    @Test
    void senha_errada_da_401() throws Exception {
        mvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(new LoginRequest("admin", "senha-errada"))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void validacao_de_entrada_da_400() throws Exception {
        mvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"\",\"password\":\"123\",\"email\":\"nao-email\"}"))
                .andExpect(status().isBadRequest());
    }
}
