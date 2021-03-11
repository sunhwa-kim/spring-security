package com.sh.spr_login.register.domain.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class RegisterRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
}
