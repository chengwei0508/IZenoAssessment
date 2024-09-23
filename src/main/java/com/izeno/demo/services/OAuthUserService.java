/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.izeno.demo.services;

import com.izeno.demo.entity.OAuthUser;
import com.izeno.demo.repos.OAuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author cw
 */
@Service
public class OAuthUserService {

    @Autowired
    private OAuthUserRepository oauthUserRepository; // Repository to access user data

    public OAuthUser getClientCredentials(String username) {
        // Fetch the user from the database by username
        OAuthUser user = oauthUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Create a new OAuthUser object with only the clientId and clientSecret
        return new OAuthUser(user.getId(), user.getUsername(), user.getPassword(), user.getClientId(), user.getClientSecret());
    }
}
