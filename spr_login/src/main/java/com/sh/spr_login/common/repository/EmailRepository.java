package com.sh.spr_login.common.repository;

import com.sh.spr_login.common.domain.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email,Integer> {
}
