package com.sh.spr_login.session.service;

import com.sh.spr_login.common.domain.entity.User;
import com.sh.spr_login.common.repository.UserRepository;
import com.sh.spr_login.exception.EmailNotFoundException;
import com.sh.spr_login.session.exception.PasswordWrongException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SessionService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SessionService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User authenticate(String email, String password) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException());

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordWrongException();
        }
        return user;
    }

}
