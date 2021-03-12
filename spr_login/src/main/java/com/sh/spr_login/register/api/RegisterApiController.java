package com.sh.spr_login.register.api;

import com.sh.spr_login.common.domain.entity.User;
import com.sh.spr_login.common.exception.WrongDomainException;
import com.sh.spr_login.register.domain.dto.RegisterRequest;
import com.sh.spr_login.register.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class RegisterApiController {

    @Autowired
    RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity create(@RequestBody RegisterRequest request) throws URISyntaxException, WrongDomainException {

        User getUser = registerService.registerUser(request);
        String url = "/register/"+getUser.getId();
        return ResponseEntity.created(new URI(url)).body("{}");
    }
}
