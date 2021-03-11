package com.sh.spr_login.session.api;

import com.sh.spr_login.session.exception.DuplicatedEmailException;
import com.sh.spr_login.session.exception.EmailNotFoundException;
import com.sh.spr_login.session.exception.PasswordWrongException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SessionErrorAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicatedEmailException.class)
    public String handleDuplicatedEmail() {
        return "{}";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailNotFoundException.class)
    public String handleEmailNotFound() {
        return "{}";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordWrongException.class)
    public String handlePasswordWrong() {
        return "{}";
    }
}
