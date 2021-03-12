package com.sh.spr_login.register.service;

import com.sh.spr_login.common.domain.entity.Email;
import com.sh.spr_login.common.domain.entity.User;
import com.sh.spr_login.common.exception.WrongDomainException;
import com.sh.spr_login.common.repository.EmailRepository;
import com.sh.spr_login.register.domain.RegisterRepostory;
import com.sh.spr_login.exception.InvalidUniqueEmailException;
import com.sh.spr_login.register.domain.dto.RegisterRequest;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class RegisterServiceTest {

    @InjectMocks
    private RegisterService registerService;

    @Mock
    private RegisterRepostory registerRepostory;

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void registerUser() throws WrongDomainException {
        String email = "tester@example.com";
        String name = "tester";
        String password = "test";

        User mockUser = givenUser(email, name, password);
        RegisterRequest request = givenRegisterRequest(email, name, password);

        given(emailRepository.findAllByDomain("example.com")).willReturn(givenEmails());
        given(registerService.registerUser(request))
                .willReturn(mockUser);

        registerService.registerUser(request);

        verify(registerRepostory).save(any());
    }

    @Test
    public void registerUserWhenAlreadyExistedEmail() throws WrongDomainException {

        String email = "tester@example.com";
        String name = "tester";
        String password = "test";
        RegisterRequest request = givenRegisterRequest(email, name, password);
        User user = User.builder().build();

        given(emailRepository.findAll()).willReturn(givenEmails());
        given(registerRepostory.findByEmail(email)).willReturn(Optional.of(user));

        assertThrows(InvalidUniqueEmailException.class,() ->
                        registerService.registerUser(request),
                "중복된 메일주소로 회원가입시 예외 발생"
        );
        verify(registerRepostory, never()).save(any());
    }

    private List<Email> givenEmails() throws WrongDomainException {
        List<Email> getList = new ArrayList<>();
        getList.add(Email.of("example.com"));
        return getList;
    }

    private User givenUser(String email, String name, String password) {
        return User.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }

    private RegisterRequest givenRegisterRequest(String email, String name, String password) {
        return RegisterRequest.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }
}