package com.sh.spr_login.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserNotFoundExcpetion extends RuntimeException {

    private static final String MESSGAE ="사용자 정보가 없습니다.";

    public UserNotFoundExcpetion() {
        super(MESSGAE);
        log.error(MESSGAE);
    }
}
