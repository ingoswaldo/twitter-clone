/*
 * @creator: Oswaldo Montes
 * @date: November 28, 2023
 *
 */
package com.koombea.twitterclone.web.app.models.projections.follow;

import com.koombea.twitterclone.web.app.shared.utilities.StringUtils;

import java.util.List;

public interface FollowerSummary {
    String getFollowerId();

    String getFollowerUsername();

    String getFollowerFullName();

    Boolean getIsFollowBack();

    default String getFollowerFullNameHumanized() {
        return StringUtils.humanizeText(getFollowerFullName());
    }

    default boolean isPresentInFollowedSummaryList(List<FollowedSummary> followedSummaryList) {
        return followedSummaryList.stream().anyMatch(item -> item.getFollowedId().equals(getFollowerId()));
    }
}
