package com.ks.hashing.api;

import com.ks.hashing.config.AbstractIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CellPhoneControllerTest extends AbstractIntegrationTest {
    private static final String HASH_CODE = "642e2f057d425e1fb11516e4245d7c9cfa3d43448ff3cdd0aa222b0f20df0241";
    private static final String CELL_PHONE_ID = "380670000001";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test
    @WithMockUser(username = "user", password = "resu", roles = "USER")
    void shouldReturnHashCode() throws Exception {
        MvcResult result = mvc.perform(get("/api/phones/{cellPhoneId}/hashes", CELL_PHONE_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String contentResponse = result.getResponse().getContentAsString();

        Assertions.assertEquals(HASH_CODE, contentResponse);
    }

    @Test
    @WithMockUser(username = "user", password = "resu", roles = "USER")
    void shouldReturnPhoneNumber() throws Exception {
        mvc.perform(get("/api/phones/hashes/{hashCode}", HASH_CODE)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(CELL_PHONE_ID));
    }
}