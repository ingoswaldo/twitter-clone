/*
 * @creator: Oswaldo Montes
 * @date: December 04, 2023
 *
 */
package com.koombea.twitterclone.web.app.components;

import com.koombea.twitterclone.web.app.services.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("followBean")
public class FollowBean {
    private final FollowService followService;

    @Autowired
    public FollowBean(FollowService followService) {
        this.followService = followService;
    }

    public Boolean isFollowedBy(String followerId, String followedId) {
        return followService.isFollowed(followerId, followedId);
    }
}
