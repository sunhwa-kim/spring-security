package com.sh.spr_login.session.domain.network;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionResquestDto {

    private String email;

    private String password;

}
