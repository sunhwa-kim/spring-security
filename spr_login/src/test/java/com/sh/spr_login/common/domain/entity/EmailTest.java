package com.sh.spr_login.common.domain.entity;

import com.sh.spr_login.common.exception.InvalidEmailException;
import com.sh.spr_login.common.exception.WrongDomainException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class EmailTest {

    @Test
    void createEmail() throws WrongDomainException {
        String domain = "gmail.com";
        Email email = Email.of(domain);
        assertThat(email.getDomain()).isEqualTo(domain);
    }

    @Test
    void wrongDomainInputWheCreateEmail() throws WrongDomainException {
        String domain = "@gmail.com";
        Email email = new Email();
        assertThrows(WrongDomainException.class,() ->
                email.of(domain),
                    "도메인 명 외 @ 기호 입력시 예외 발생"
        );
    }

    @Test
    void impossibleDomainInputWheCreateEmail() throws WrongDomainException {
        String domain = "gmailc.om";
        Email email = new Email();
        assertThrows(InvalidEmailException.class,() ->
                        email.of(domain),
                "불가능한 도메인 예외 발생"
        );
    }
}