package com.sh.spr_login.session.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.spr_login.common.domain.entity.User;
import com.sh.spr_login.common.exception.WrongDomainException;
import com.sh.spr_login.register.domain.dto.RegisterRequest;
import com.sh.spr_login.session.domain.network.SessionResquestDto;
import com.sh.spr_login.session.service.SessionService;
import com.sh.spr_login.session.exception.PasswordWrongException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SesseionControllerTest {

        @Autowired
        SesseionController sesseionController;

        @MockBean
        SessionService sessionService;

        @Autowired
        private ObjectMapper objectMapper;

        private MockMvc mvc;

        @BeforeEach
        private void setup() {
            mvc = MockMvcBuilders.standaloneSetup(sesseionController)
                    .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                    .alwaysDo(print())
                    .build();
        }

        @Test
        public void create() throws Exception {
            String name = "tester";
            String email = "tester@example.com";
            String password = "userEncodedPwd";  // test for accessTocken

            User mockUser = User.builder().password(password).build();

            given(sessionService.authenticate(email, password)).willReturn(mockUser);

            mvc.perform(post("/session")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"email\":\"tester@example.com\",\"" +"password\":\"userEncodedPwd\"}"))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("location", "/session"))
            .andExpect(content().string("{\"accessTocken\":\"userEncode\"}")) // test for accessTocken
            ;

            verify(sessionService).authenticate(eq(email), eq(password));

        }

    @Test
    public void createWithInvalidPassword() throws Exception {
        String email = "tester@example.com";
        String password = "wrong";
        SessionResquestDto request = givenRequest(email, password);
        when(sessionService.authenticate(email, password))
                .thenThrow(PasswordWrongException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(toJsonString(request)))
                .andExpect(status().isBadRequest())
        ;
        verify(sessionService).authenticate(eq("tester@example.com"), eq("wrong"));
    }

    @Test
    public void createWithInvalidEmail() throws Exception {
        given(sessionService.authenticate("tester@example.com", "wrong"))
                .willThrow(PasswordWrongException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@example.com\",\"" +"password\":\"wrong\"}"))
                .andExpect(status().isBadRequest())
        ;

        verify(sessionService).authenticate(eq("tester@example.com"), eq("wrong"));
    }

    private SessionResquestDto givenRequest(String email, String password) {
        SessionResquestDto dto = new SessionResquestDto(email, password);
        return dto;
    }
    private String toJsonString(SessionResquestDto dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }

}