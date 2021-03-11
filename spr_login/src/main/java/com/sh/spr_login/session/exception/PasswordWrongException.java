package com.sh.spr_login.session.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordWrongException extends RuntimeException{

    private static final String MESSGAE = "잘못된 비밀번호를 입력 하셨습니다.";

    public PasswordWrongException() {
        super(MESSGAE);
        log.error(MESSGAE);
    }
}
