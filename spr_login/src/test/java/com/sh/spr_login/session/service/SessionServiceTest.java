package com.sh.spr_login.session.service;

import com.sh.spr_login.common.domain.entity.User;
import com.sh.spr_login.common.repository.UserRepository;
import com.sh.spr_login.session.exception.EmailNotFoundException;
import com.sh.spr_login.session.exception.PasswordWrongException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class SessionServiceTest {

    @InjectMocks
    private SessionService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new SessionService(userRepository, passwordEncoder);
    }

    @Test
    public void authenticateTest() {
        String email = "tester@example.com";
        String password = "test";

        User mockUser = User.builder().email(email).build();

        given(userRepository.findByEmail(email))
                .willReturn(Optional.of(mockUser));

        given(passwordEncoder.matches(any(), any())).willReturn(true);

        User user = userService.authenticate(email, password);

        assertThat(user.getEmail()).isEqualTo(email);
    }

    @Test
    public void whenNotFoundEmailAuthenticateTest() {
        String email = "none@example.com";
        String password = "test";

        given(userRepository.findByEmail(email))
                .willReturn(Optional.empty());

        given(passwordEncoder.matches(any(), any())).willReturn(true);

        assertThrows(EmailNotFoundException.class,() ->
                        userService.authenticate(email, password),
                "해당 이메일 정보 없을 때 예외 발생"
        );
    }

    @Test
    public void whenWrongPasswordEnterAuthenticateTest() {
        String email = "none@example.com";
        String password = "wrong";

        User mockUser = User.builder().email(email).password("test").build();

        given(userRepository.findByEmail(email))
                .willReturn(Optional.of(mockUser));

//        given(passwordEncoder.matches(any(), any())).willReturn(true);

        assertThrows(PasswordWrongException.class,() ->
                        userService.authenticate(email, password),
                "잘못된 비밀번호 입력시 예외 발생"
        );
    }

}
