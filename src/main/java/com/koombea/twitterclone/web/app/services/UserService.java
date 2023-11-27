/*
 * @creator: Oswaldo Montes
 * @date: November 17, 2023
 *
 */
package com.koombea.twitterclone.web.app.services;

import com.koombea.twitterclone.web.app.models.entities.Role;
import com.koombea.twitterclone.web.app.models.entities.User;
import com.koombea.twitterclone.web.app.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public User create(String email, String username, String fullName, String password) {
        String passwordEncrypted = passwordEncoder.encode(password);
        User user = new User(email, username, fullName, passwordEncrypted, passwordEncrypted);
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }

    public User findByUsername(String username) throws EntityNotFoundException {
        Optional<User> user = userRepository.findByUsernameAndEnabled(username, true);
        if (user.isEmpty()) throw new EntityNotFoundException("❌ The user does not exist!!");

        return user.get();
    }
}
