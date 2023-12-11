/*
 * @creator: Oswaldo Montes
 * @date: November 20, 2023
 *
 */
package com.koombea.twitterclone.web.app.services;

import com.koombea.twitterclone.web.app.models.entities.Role;
import com.koombea.twitterclone.web.app.models.projections.user.AuthenticationOnly;
import com.koombea.twitterclone.web.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AuthenticationOnly> user = userRepository.findByUsernameAndEnabled(username, true, AuthenticationOnly.class);
        if (user.isEmpty()) throw new UsernameNotFoundException("Username does not found");

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.get().getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }

        if (authorities.isEmpty()) throw new UsernameNotFoundException("Username does not have assigned roles");

        AuthenticationOnly authUser = user.get();
        return new org.springframework.security.core.userdetails.User(username, authUser.getPassword(), authUser.getEnabled(), true, true, true, authorities);
    }
}
