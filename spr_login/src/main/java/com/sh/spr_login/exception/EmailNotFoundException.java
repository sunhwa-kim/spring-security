package com.sh.spr_login.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailNotFoundException extends RuntimeException{

    private static final String MESSGAE ="해당 도메인 정보로 등록 불가 합니다.";

    public EmailNotFoundException() {
        super(MESSGAE);
        log.error(MESSGAE);
    }
}

