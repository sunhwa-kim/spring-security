package com.sh.spr_login.common.service;

import com.sh.spr_login.common.domain.entity.User;
import com.sh.spr_login.common.repository.UserRepository;
import com.sh.spr_login.exception.UserNotFoundExcpetion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.filter;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<User> argumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    void getUsers() {
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(User.builder()
                .email("tester@example.com")
                .name("tester")
                .level(1L)
                .build());
        given(userRepository.findAll()).willReturn(mockUsers);
        List<User> users = userService.getUsers();

        User user = users.get(0);
        assertThat(user.getName()).isEqualTo("tester");
    }

    @Test
    void addUserTest() {
        String email = "admin@exmaple.com";
        String name = "administrator";
        User mockUser = User.builder().email(email).name(name).build();

        userService.addUser(email, name);

        verify(userRepository, times(1)).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getEmail()).isEqualTo(email);
        assertThat(argumentCaptor.getValue().getName()).isEqualTo(name);
    }

    @Test
    void updateUser() {
        Long id = 1004L;
        String email = "admin@exmaple.com";
        String name = "changeName";
        Long level = 100L;

        User mockUser = User.builder().id(id)
                .email(email)
                .name("Administrator")
                .level(1L)
                .build();

        given(userRepository.findById(id)).willReturn(Optional.of(mockUser));

        User user = userService.updateUser(id, email, name, level);

        verify(userRepository).findById(eq(id));

        assertThat(user.getName()).isEqualTo("changeName");
        assertThat(user.isAdmin()).isTrue();
    }

    @Test
    void updateUserNotFoundException() {
        Long id = 1004L;
        String email = "admin@exmaple.com";
        String name = "changeName";
        Long level = 100L;

        User mockUser = User.builder().id(id)
                .email(email)
                .name("Administrator")
                .level(1L)
                .build();

        given(userRepository.findById(id)).willReturn(Optional.empty());

        assertThrows(UserNotFoundExcpetion.class,() ->
                        userService.updateUser(id, email, name, level),
                "회원 정보 변경시 조회 결과 없음 예외 발생"
        );
    }

    @Test
    public void deactiveUser() {
        Long id = 1000L;

        User mockUser = User.builder()
                .id(id)
                .email("admin@exmaple.com")
                .name("Administrator")
                .level(100L)
                .build();

        given(userRepository.findById(id)).willReturn(Optional.of(mockUser));

       User user = userService.deactiveUser(1000L);

        verify(userRepository).findById(1000L);

        assertThat(user.isAdmin()).isFalse();
        assertThat(user.isActive()).isFalse();
    }

    @Test
    void deactiveUserNotFoundException() {
        Long id = 1004L;
        String email = "admin@exmaple.com";
        String name = "changeName";
        Long level = 100L;

        User mockUser = User.builder().id(id)
                .email(email)
                .name("Administrator")
                .level(1L)
                .build();

        given(userRepository.findById(id)).willReturn(Optional.empty());

        assertThrows(UserNotFoundExcpetion.class,() ->
                        userService.deactiveUser(1000L),
                "회원 정보 변경시 조회 결과 없음 예외 발생"
        );
    }

}