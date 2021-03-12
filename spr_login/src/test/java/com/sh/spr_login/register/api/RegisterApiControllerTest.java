package com.sh.spr_login.register.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.spr_login.common.domain.entity.User;
import com.sh.spr_login.common.exception.WrongDomainException;
import com.sh.spr_login.register.api.RegisterApiController;
import com.sh.spr_login.register.domain.RegisterRepostory;
import com.sh.spr_login.register.domain.dto.RegisterRequest;
import com.sh.spr_login.register.service.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RegisterApiControllerTest {

    @Autowired
    RegisterApiController registerApiController;

    @MockBean
    private RegisterService registerService;

    @MockBean
    private RegisterRepostory registerRepostory;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mvc;

    @BeforeEach
    private void setup() {
        mvc = MockMvcBuilders.standaloneSetup(registerApiController)
                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                .alwaysDo(print())
                .build();
    }

    @Test
    public void create() throws Exception, WrongDomainException {
        String email = "tester@example.com";
        String name = "testName";
        String password = "test";

        User mockUser = User.builder()
                .id(1000L)
                .email(email)
                .name(name)
                .password(password)
                .build();

        RegisterRequest request = givenRegisterRequest(email, name, password);
        given(registerService.registerUser(request))
                .willReturn(mockUser);

        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(request)))
                .andExpect(status().isCreated())
                ;

        verify(registerService).registerUser(eq(request));
    }
/*
    private User givenUser(String email, String name, String password) {
        return User.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }*/

    private RegisterRequest givenRegisterRequest(String email, String name, String password) {
        return RegisterRequest.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }
    private String toJsonString(RegisterRequest dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }

}