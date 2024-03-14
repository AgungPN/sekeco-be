package com.cashier.system.skecobe.services;

import com.cashier.system.skecobe.entities.User;
import com.cashier.system.skecobe.enums.Role;
import com.cashier.system.skecobe.repositories.UserRepository;
import com.cashier.system.skecobe.responses.LoginResponse;
import com.cashier.system.skecobe.security.JwtIssuer;
import com.cashier.system.skecobe.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public LoginResponse attemptLogin(String username, String password) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrincipal) authentication.getPrincipal();
        // NOTE: I need the user role, but return from practice is null, so I will get it from the database. If you can get the role from the principal, please do so. and remove the following lines
        User user = userRepository.findById(principal.getUserId()).orElseThrow();

        var token = jwtIssuer.issue(JwtIssuer.Request.builder()
                .userId(principal.getUserId())
                .username(principal.getUsername())
                .roles(principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build());

        System.out.println(user.getRole().getClass());
        return LoginResponse.builder()
                .token(token)
                .username(principal.getUsername())
                .role(user.getRole())
                .build();
    }
}
