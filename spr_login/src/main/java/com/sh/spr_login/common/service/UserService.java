package com.sh.spr_login.common.service;

import com.sh.spr_login.common.repository.UserRepository;
import com.sh.spr_login.common.domain.entity.User;
import com.sh.spr_login.exception.UserNotFoundExcpetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();

        return users;
    }

    public User addUser(String email, String name) {
        User newUser = User.builder()
                .email(email)
                .name(name)
                .level(1L)
                .build();
        return userRepository.save(newUser);
    }

    public User updateUser(Long id, String email, String name, Long level) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundExcpetion());
        user.update(id, email, name, level);  // Todo : email 확인 추가
        return user;
    }

    public User deactiveUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundExcpetion());
        user.deactivated();
        return user;
    }



}
