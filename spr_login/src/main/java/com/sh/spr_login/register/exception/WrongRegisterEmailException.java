package com.sh.spr_login.register.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WrongRegisterEmailException extends RuntimeException {
    private static final String MESSGAE = "잘못된 이메일 주소 입니다.";

    public WrongRegisterEmailException() {
        super(MESSGAE);
        log.error(MESSGAE);
    }
}
