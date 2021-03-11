package com.sh.spr_login.common.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WrongDomainException extends Throwable {
    private static final String MESSGAE = "를 제외한 도메인 명만 입력해 주세요.";

    public WrongDomainException(String wrongString) {
        super(wrongString+MESSGAE);
        log.error(MESSGAE);
    }
}
