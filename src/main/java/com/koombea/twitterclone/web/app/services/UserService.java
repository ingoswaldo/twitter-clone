/*
 * @creator: Oswaldo Montes
 * @date: November 17, 2023
 *
 */
package com.koombea.twitterclone.web.app.services;

import com.koombea.twitterclone.web.app.models.entities.Role;
import com.koombea.twitterclone.web.app.models.entities.User;
import com.koombea.twitterclone.web.app.models.projections.user.IdOnly;
import com.koombea.twitterclone.web.app.models.projections.user.NamesWithIdOnly;
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
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
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

    public IdOnly findIdByUsername(String username) throws EntityNotFoundException {
        Optional<IdOnly> user = userRepository.findByUsernameAndEnabled(username, true, IdOnly.class);
        if (user.isEmpty()) throw new EntityNotFoundException("❌ The user does not exist!!");

        return user.get();
    }

    public NamesWithIdOnly findNamesByUsername(String username) throws EntityNotFoundException {
        Optional<NamesWithIdOnly> user = userRepository.findByUsernameAndEnabled(username, true, NamesWithIdOnly.class);
        if (user.isEmpty()) throw new EntityNotFoundException("❌ The user does not exist!!");

        return user.get();
    }

    public User findUserByUsername(String username) throws EntityNotFoundException {
        Optional<User> user = userRepository.findByUsernameAndEnabled(username, true, User.class);
        if (user.isEmpty()) throw new EntityNotFoundException("❌ The user does not exist!!");

        return user.get();
    }

    public NamesWithIdOnly findNamesById(String id) throws EntityNotFoundException {
        Optional<NamesWithIdOnly> user = userRepository.findByIdAndEnabled(id, true, NamesWithIdOnly.class);
        if (user.isEmpty()) throw new EntityNotFoundException("❌ The user does not exist!!");

        return user.get();
    }
}
