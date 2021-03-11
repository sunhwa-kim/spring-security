package com.sh.spr_login.session.api;

import com.sh.spr_login.common.domain.entity.User;
import com.sh.spr_login.session.domain.network.SessionResponseDto;
import com.sh.spr_login.session.domain.network.SessionResquestDto;
import com.sh.spr_login.session.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class SesseionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping("/session")
    public ResponseEntity<SessionResponseDto> create(@RequestBody SessionResquestDto request) throws URISyntaxException {

        String email = request.getEmail();
        String password = request.getPassword();
        User user = sessionService.authenticate(email, password);

        String accessToken = user.getAccessTocken();

        String url = "/session";
        return ResponseEntity.created(new URI(url))
                .body(
                        SessionResponseDto.builder()
                                .accessTocken(accessToken).build());
    }
}
