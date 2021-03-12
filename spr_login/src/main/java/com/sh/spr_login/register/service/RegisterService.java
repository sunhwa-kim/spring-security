package com.sh.spr_login.register.service;

import com.sh.spr_login.common.domain.entity.Email;
import com.sh.spr_login.common.exception.WrongDomainException;
import com.sh.spr_login.common.repository.EmailRepository;
import com.sh.spr_login.common.service.UserService;
import com.sh.spr_login.exception.EmailNotFoundException;
import com.sh.spr_login.exception.InvalidUniqueEmailException;
import com.sh.spr_login.common.domain.entity.User;
import com.sh.spr_login.register.domain.RegisterRepostory;
import com.sh.spr_login.register.domain.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RegisterService {

    RegisterRepostory registerRepostory;
    EmailRepository emailRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterService(RegisterRepostory registerRepostory, EmailRepository emailRepository, PasswordEncoder passwordEncoder) {
        this.registerRepostory = registerRepostory;
        this.emailRepository = emailRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User registerUser(RegisterRequest request) throws WrongDomainException {

        // FE에서 기본적 email 확인 + 메일주소 리스트 체크
        if(!checkEmail(request.getEmail())) throw new EmailNotFoundException();

        Optional<User> checkExistedEmail = registerRepostory.findByEmail(request.getEmail());
        if(checkExistedEmail.isPresent()){
            throw new InvalidUniqueEmailException();
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(encodedPassword)
                .level(1L) // Todo 등급별 변경
                .role(request.getRole())
                .build();

        User getUser = registerRepostory.save(user);
        return getUser;
    }

    private boolean checkEmail(String emailStr) throws WrongDomainException {
        String domain = emailStr.substring(emailStr.indexOf("@") + 1);
        List<Email> emails = emailRepository.findAllByDomain(domain.trim());
        for (Email email : emails) {
            if(email.getDomain().equals(domain) ) return true;
        }
        return false;
    }

}
