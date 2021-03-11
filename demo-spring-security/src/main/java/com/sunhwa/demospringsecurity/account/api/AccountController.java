package com.sunhwa.demospringsecurity.account.api;

import com.sunhwa.demospringsecurity.account.domain.entity.Account;
import com.sunhwa.demospringsecurity.account.repository.AccountRepository;
import com.sunhwa.demospringsecurity.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    // 인증정보 추가 헨들러 - 회원가입의 간단 예제

    @Autowired
    AccountService accountService;

    @GetMapping("/account/{role}/{username}/{password}")
    public Account createAccount(@ModelAttribute Account account) {
        return accountService.createNew(account);
    }
}
