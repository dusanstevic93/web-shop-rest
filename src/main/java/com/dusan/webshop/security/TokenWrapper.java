package com.dusan.webshop.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenWrapper {

    private String token;
    private long expire;
}
