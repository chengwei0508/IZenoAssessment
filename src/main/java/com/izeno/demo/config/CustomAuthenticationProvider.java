/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.izeno.demo.config;

import com.izeno.demo.entity.OAuthUser;
import com.izeno.demo.services.OAuthUserService;
import com.izeno.demo.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 *
 * @author cw
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private OAuthUserService oauthUserService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        OAuthUser user = oauthUserService.getClientCredentials(username);

        if (user == null || !Util.verifyPassword(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        UserDetails userDetails = User.withUsername(username)
                .password(user.getPassword()) // Store hashed password
                .authorities(Collections.emptyList()) // Add roles/authorities if needed
                .build();

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
