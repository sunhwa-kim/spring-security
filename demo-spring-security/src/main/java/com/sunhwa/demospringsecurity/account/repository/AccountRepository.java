package com.sunhwa.demospringsecurity.account.repository;

import com.sunhwa.demospringsecurity.account.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
}
