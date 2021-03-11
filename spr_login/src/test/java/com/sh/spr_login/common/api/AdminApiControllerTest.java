package com.sh.spr_login.common.api;

import com.sh.spr_login.common.domain.entity.User;
import com.sh.spr_login.common.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class AdminApiControllerTest {

    @Autowired
    AdmApiController admApiController;

    @MockBean
    UserService userService;

    private MockMvc mvc;

    @BeforeEach
    private void setup() {
        mvc = MockMvcBuilders.standaloneSetup(admApiController)
                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                .alwaysDo(print())
                .build();
    }

    @Test
    public void list() throws Exception {
        List<User> users = new ArrayList<>();

        users.add(User.builder()
                .email("tester@example.com")
                .name("tester")
                .level(1L)
                .build());
        given(userService.getUsers()).willReturn(users);

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..name").value(users.get(0).getName()))
        ;
    }

    @Test()
    @DisplayName("관리자 사용자 등록")
    void create() throws Exception {
        // partner , admin 추가 등
        String email = "admin@exmaple.com";
        String name = "administrator";
        User user = User.builder().email(email).name(name).level(1L).build();
        given(userService.addUser(email, name)).willReturn(user);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"admin@exmaple.com\",\"name\":\"administrator\"}"))
                .andExpect(status().isCreated());

        verify(userService).addUser(email, name);
    }

    @Test
    void update() throws Exception {

//        User mockUser = User.builder().id(id).email(email).name(name).level(level).build();

//        given(userService.updateUser(id, email, name, level)).willReturn(mockUser);

        mvc.perform(patch("/users/1000")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1004,\"email\":\"admin@exmaple.com\"," +
                        "\"name\":\"administrator2\",\"level\":100}"))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(id))
        ;

        Long id = 1000L;
        String email = "admin@exmaple.com";
        String name = "administrator2";
        Long level = 100L;
        verify(userService).updateUser(eq(id), eq(email), eq(name), eq(level));
    }

    @Test
    public void deactivate() throws Exception {
        mvc.perform(delete("/users/1000"))
                .andExpect(status().isOk());

        verify(userService).deactiveUser(1000L);
    }
}