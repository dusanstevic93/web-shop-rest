package com.dusan.webshop.dao.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationProjection {

    private long userId;
    private String username;
    private String password;
    private long roleId;
}
