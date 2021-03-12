package com.sh.spr_login.common.domain.entity;

import com.sh.spr_login.common.exception.InvalidEmailException;
import com.sh.spr_login.common.exception.WrongDomainException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Email {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String domain;


    public static Email of(String domain) throws WrongDomainException {
        Email email = new Email();
        email.checkDomain(domain.trim());
        email.domain = domain;
        return email;
    }

    public void checkDomain(String domain) throws WrongDomainException {
        if(domain.indexOf("@")!=-1) throw new WrongDomainException("@");

        int pointIndex = domain.length() - 4;
        if(domain.indexOf(".")==-1 || domain.indexOf(".") > pointIndex) throw new InvalidEmailException();
    }
}
