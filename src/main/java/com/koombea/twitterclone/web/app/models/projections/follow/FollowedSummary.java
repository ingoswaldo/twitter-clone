/*
 * @creator: Oswaldo Montes
 * @date: November 28, 2023
 *
 */
package com.koombea.twitterclone.web.app.models.projections.follow;

import org.springframework.beans.factory.annotation.Value;

public interface FollowedSummary {
    String getFollowedId();

    String getFollowedUsername();

    String getFollowedFullName();

    @Value("#{@followBean.isFollowedBy(args[0],target.followedId)}")
    Boolean isFollowedBy(String followerId);

    default String getFollowedFullNameHumanized() {
        return getFollowedFullName().substring(0,1).toUpperCase().concat(getFollowedFullName().substring(1));
    }
}
