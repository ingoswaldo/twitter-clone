/*
 * @creator: Oswaldo Montes
 * @date: November 28, 2023
 *
 */
package com.koombea.twitterclone.web.app.models.projections.user;

import com.koombea.twitterclone.web.app.shared.utilities.StringUtils;

public interface NamesWithIdOnly {
    String getId();

    String getFullName();

    String getUsername();

    default String getFullNameHumanized() {
        return StringUtils.humanizeText(getFullName());
    }
}
