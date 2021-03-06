package com.sh.spr_login.common.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String role;

    @NotNull
    private Long level;

    public void update(Long id, String email, String name, Long level) {
        // id는 level 변경시..
        this.id = id;
        this.email = email;
        this.name = name;
        this.level = level;
    }

    public boolean isAdmin() {
        return level >= 10;
    }

    public boolean isActive() {
        return level > 0;
    }

    public void deactivated() {
        level = 0L;
    }

    @JsonIgnore
    public String getAccessTocken() {
        if (password == null) {
            return "";
        }
        return password.substring(0, 10);
    }
}
