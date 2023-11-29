/*
 * @creator: Oswaldo Montes
 * @date: November 28, 2023
 *
 */
package com.koombea.twitterclone.web.app.models.projections.follow;

public interface FollowedSummary {
    String getFollowedUsername();

    String getFollowedFullName();

    default String getFollowedFullNameHumanized() {
        return getFollowedFullName().substring(0,1).toUpperCase().concat(getFollowedFullName().substring(1));
    }
}
