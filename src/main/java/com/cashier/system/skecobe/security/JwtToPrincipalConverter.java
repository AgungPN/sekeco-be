package com.cashier.system.skecobe.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.cashier.system.skecobe.enums.Role;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Component
public class JwtToPrincipalConverter {
    public UserPrincipal convert(DecodedJWT jwt) {
        var authorityList = getClaimOrEmptyList(jwt, "role").stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        System.out.println(jwt.getClaim("role").asString());
        return UserPrincipal.builder()
                .userId( Long.parseLong(jwt.getSubject()) )
                .username( jwt.getClaim("username").asString() )
                .role(jwt.getClaim("role").as(Role.class))
                .authorities( authorityList )
                .build();
    }

    private List<String> getClaimOrEmptyList(DecodedJWT jwt, String claim) {
        if (jwt.getClaim(claim).isNull()) return List.of();
        return jwt.getClaim(claim).asList(String.class);
    }
}
