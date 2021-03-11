package com.sh.spr_login.register.service;

import com.sh.spr_login.common.domain.entity.Email;
import com.sh.spr_login.common.repository.EmailRepository;
import com.sh.spr_login.exception.InvalidUniqueEmailException;
import com.sh.spr_login.common.domain.entity.User;
import com.sh.spr_login.register.domain.RegisterRepostory;
import com.sh.spr_login.register.domain.dto.RegisterRequest;
import com.sh.spr_login.register.exception.WrongRegisterEmailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterService {

    private RegisterRepostory registerRepostory;
    private EmailRepository emailRepository;
    private PasswordEncoder passwordEncoder;

    public User registerUser(RegisterRequest request) {

        // FE에서 기본적 email 확인 + 메일주소 리스트 체크
        checkEmail(request.getEmail());

        Optional<User> checkExistedEmail = registerRepostory.findByEmail(request.getEmail());
        if(checkExistedEmail.isPresent()){
            throw new InvalidUniqueEmailException();
        }

//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();  // 인터페이스로 받는다
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(encodedPassword)
                .level(1L) // Todo 등급별 변경
                .build();

        User getUser = registerRepostory.save(user);
        return getUser;
    }

    private void checkEmail(String email) {
        email = email.substring(email.indexOf("@") + 1);
        List<Email> emails = emailRepository.findAll();
        for (Email e : emails) {
            if (!e.getDomain().equals(email)) {
                throw new WrongRegisterEmailException();
            }
        }
    }

//    private PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
