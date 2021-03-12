package com.sh.spr_login.common.api;

import com.sh.spr_login.common.domain.entity.User;
import com.sh.spr_login.common.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RestController("/amdin")
public class AdmApiController {
    @Autowired
    private UserService userService;

    // level : 1. user, 2.partner , 3.admin
    @GetMapping("/users")
    public List<User> list(){
        List<User> users = userService.getUsers();
        return users;
    }

    @PostMapping("/users")
    public ResponseEntity create(@RequestBody User request) throws URISyntaxException {
        // 관리자 추가, 파트너사 추가 != 일반 사용자 회원가입
        String email = request.getEmail();
        String name = request.getName();

        User user = userService.addUser(email, name);

        String url = "/users/" + user.getId();

        log.info(user.getName());
        return ResponseEntity.created(new URI(url)).body(user);
    }

    @PatchMapping("/users/{id}")
    public String update(@PathVariable("id") Long id, @RequestBody User request) {
        String email = request.getEmail();
        String name = request.getName();
        Long level = request.getLevel();

        userService.updateUser(id, email, name, level);

        return "{}";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable Long id) {
        userService.deactiveUser(id);
        return "{}";
    }
}
