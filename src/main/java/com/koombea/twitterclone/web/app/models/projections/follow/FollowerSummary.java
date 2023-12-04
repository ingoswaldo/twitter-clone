/*
 * @creator: Oswaldo Montes
 * @date: November 28, 2023
 *
 */
package com.koombea.twitterclone.web.app.models.projections.follow;

import com.koombea.twitterclone.web.app.shared.utilities.StringUtils;
import org.springframework.beans.factory.annotation.Value;

public interface FollowerSummary {
    String getFollowerId();

    String getFollowerUsername();

    String getFollowerFullName();

    Boolean getIsFollowBack();

    @Value("#{@followBean.isFollowedBy(args[0],target.followerId)}")
    Boolean isFollowedBy(String followerId);

    default String getFollowerFullNameHumanized() {
        return StringUtils.humanizeText(getFollowerFullName());
    }
}
