package com.sh.spr_login.register.domain;

import com.sh.spr_login.common.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisterRepostory extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByName(String email);
}
