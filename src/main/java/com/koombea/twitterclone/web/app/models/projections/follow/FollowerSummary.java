/*
 * @creator: Oswaldo Montes
 * @date: November 28, 2023
 *
 */
package com.koombea.twitterclone.web.app.models.projections.follow;

import com.koombea.twitterclone.web.app.shared.utilities.StringUtils;

public interface FollowerSummary {
    String getFollowerId();

    String getFollowerUsername();

    String getFollowerFullName();

    default String getFollowerFullNameHumanized() {
        return StringUtils.humanizeText(getFollowerFullName());
    }
}
