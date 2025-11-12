package com.minitb.utils;

import io.smallrye.jwt.build.Jwt;

public class JwtTest {
    public static void main(String[] args) {
        String token = Jwt.issuer("mini-tb")
                .upn("test")
                .claim("groups", java.util.List.of("TEST"))
                .expiresAt(java.time.Instant.now().getEpochSecond() + 3600)
                .sign();
        System.out.println(token);
    }
}
