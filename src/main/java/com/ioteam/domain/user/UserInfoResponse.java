package com.ioteam.domain.user;

import com.ioteam.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserInfoResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String role;

    public UserInfoResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole().name();
    }
}
