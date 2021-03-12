package com.sh.spr_login.common.domain;

import com.sh.spr_login.common.domain.entity.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void creat() {
        String email = "admin@exmaple.com";
        String name = "Administrator";

        User user = User.builder().email(email).name(name).level(100L).build();
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.isAdmin()).isTrue();
        assertThat(user.isActive()).isTrue();

    }

    @Test
    void deacitvated() {
        String email = "admin@exmaple.com";
        String name = "Administrator";

        User user = User.builder().email(email).name(name).level(100L).build();
        user.deactivated();
        assertThat(user.isActive()).isFalse();
    }

    @Test
    void getAccessTockenTest() {
        String testPwd = "userEncodedPwd";
        User user = User.builder().password(testPwd).build();

        assertThat(user.getAccessTocken()).isEqualTo(testPwd.substring(0,10));  // pwd의 10글자만
    }


    @Test
    void getAccessTockenWithoutPasswordTest() {
        User user = new User();
        assertThat(user.getAccessTocken()).isEqualTo("");
    }

}