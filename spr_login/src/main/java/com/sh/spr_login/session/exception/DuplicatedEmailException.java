package com.sh.spr_login.session.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DuplicatedEmailException extends RuntimeException{

    private static final String MESSGAE = "임 로그인 된 사용자 입니다.";

    public DuplicatedEmailException() {
        super(MESSGAE);
        log.error(MESSGAE);
    }
}
