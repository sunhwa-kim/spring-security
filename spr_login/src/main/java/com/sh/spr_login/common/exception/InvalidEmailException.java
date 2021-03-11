package com.sh.spr_login.common.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidEmailException extends RuntimeException {

    private static final String MESSGAE = "잘못된 도메인명 입니다.";

    public InvalidEmailException() {
        super(MESSGAE);
        log.error(MESSGAE);
    }
}
