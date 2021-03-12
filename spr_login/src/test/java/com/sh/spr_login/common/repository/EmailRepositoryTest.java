package com.sh.spr_login.common.repository;

import com.sh.spr_login.common.domain.entity.Email;
import com.sh.spr_login.common.exception.WrongDomainException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@Slf4j
@DataJpaTest
class EmailRepositoryTest {

    @Autowired
    EmailRepository emailRepository;

    @Test
    @Transactional
    void getEmail() throws WrongDomainException {
        String result = "";
        String domain = "example.com";
        Email getEmail = Email.of(domain);
        Email save = emailRepository.save(getEmail);

        assertThat(save.getDomain()).isEqualTo(domain);

        List<Email> emails = emailRepository.findAllByDomain(domain);
        for (Email email : emails) {
            if(email.getDomain().equals(domain) ) result = email.getDomain() ;
        }
        assertThat(result).isEqualTo(domain);
    }
}