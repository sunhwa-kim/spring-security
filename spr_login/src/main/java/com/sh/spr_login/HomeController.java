package com.sh.spr_login;

import com.sh.spr_login.common.domain.entity.Email;
import com.sh.spr_login.common.exception.WrongDomainException;
import com.sh.spr_login.common.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    EmailRepository emailRepository;

    @GetMapping("/")
    public String index(Model model, Principal principal) throws WrongDomainException {
        Email email = Email.of("example.com");
        emailRepository.save(email);
        // anyone = login + no login
        if (principal == null) {
            model.addAttribute("message", "Hello Spring Security");
        } else {
            model.addAttribute("message", "Hello, "+principal.getName());
        }
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("message", "only Admin :"+principal.getName());
        return "admin";
    }
}
