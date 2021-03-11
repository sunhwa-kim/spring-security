package com.sh.spr_login.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidUniqueEmailException extends RuntimeException{

    private static final String MESSGAE ="해당 메일정보는 이미 등록되어 있습니다.";

    public InvalidUniqueEmailException() {
        super(MESSGAE);
        log.error(MESSGAE);
    }
}

