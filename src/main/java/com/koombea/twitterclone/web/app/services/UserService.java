package com.koombea.twitterclone.web.app.services;

import com.koombea.twitterclone.web.app.models.entities.User;
import com.koombea.twitterclone.web.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
 * @creator: Oswaldo Montes
 * @date: November 17, 2023
 *
 */
@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public User create(String email, String username, String fullName, String password) {
        String passwordEncrypted = passwordEncoder.encode(password);
        return userRepository.save(new User(email, username, fullName, passwordEncrypted, passwordEncrypted));
    }

    public boolean existsBy(String fieldName, String value) {
        try {
            String methodName = "existsBy" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method method = userRepository.getClass().getMethod(methodName, String.class);
            return !((boolean) method.invoke(userRepository, value));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NullPointerException e) {
            throw new RuntimeException(e);
        }
    }
}
