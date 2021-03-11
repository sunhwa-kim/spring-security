package com.sh.spr_login.session.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailNotFoundException extends RuntimeException{

    private static final String MESSGAE = "해당 메일 정보는 없습니다.";

    public EmailNotFoundException() {
        super(MESSGAE);
        log.error(MESSGAE);
    }
}
