package com.sh.spr_login.session;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionDto {

    private String accessTocken;
}
