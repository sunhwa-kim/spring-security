package com.sh.spr_login.common.domain.entity;

import com.sh.spr_login.common.exception.InvalidEmailException;
import com.sh.spr_login.common.exception.WrongDomainException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Slf4j
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Email {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String domain;

    public static Email of(String domain) throws WrongDomainException {
        Email email = new Email();
        email.checkDomain(domain);
        email.domain = domain;
        return email;
    }

    public void checkDomain(String domain) throws WrongDomainException {
        if(domain.indexOf("@")!=-1) throw new WrongDomainException("@");

        int pointIndex = domain.length() - 4;
//        log.info(">> {}, {} : {}",pointIndex,domain.length(),domain.indexOf(".") > pointIndex);
        if(domain.indexOf(".")==-1 || domain.indexOf(".") > pointIndex) throw new InvalidEmailException();
    }
}
