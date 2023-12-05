/*
 * @creator: Oswaldo Montes
 * @date: November 30, 2023
 *
 */
package com.koombea.twitterclone.web.app.models.projections.post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface PostSummary {
    String getMessage();

    LocalDateTime getCreatedAt();

    String getUsername();

    String getFullName();

    default String getFullNameHumanized(String authenticatedUsername) {
        if (getUsername().equals(authenticatedUsername)) return "Me";

        return getFullName().substring(0,1).toUpperCase().concat(getFullName().substring(1));
    }

    default String getCreatedAtFormatted() {
        return getCreatedAt().format(DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' HH:mm"));
    }
}
