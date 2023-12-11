/*
 * @creator: Oswaldo Montes
 * @date: November 17, 2023
 *
 */
package com.koombea.twitterclone.web.app.services;

import com.koombea.twitterclone.web.app.repositories.ValidationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidatorService {
    private final ValidationRepository validationRepository;

    @Autowired
    public ValidatorService(ValidationRepository validationRepository) {
        this.validationRepository = validationRepository;
    }

    public boolean existsBy(String tableName, String columnName, Object value) {
        return validationRepository.existBy(tableName, columnName, value);
    }
}
