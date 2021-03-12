package com.sh.spr_login.register.domain.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String password;
    @NotBlank
    private String email;

    private String role;

}
