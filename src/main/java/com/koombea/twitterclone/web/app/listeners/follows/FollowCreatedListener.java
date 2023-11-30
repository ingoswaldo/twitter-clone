/*
 * @creator: Oswaldo Montes
 * @date: November 30, 2023
 *
 */
package com.koombea.twitterclone.web.app.listeners.follows;

import com.koombea.twitterclone.web.app.events.follows.FollowCreatedEvent;
import com.koombea.twitterclone.web.app.services.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class FollowCreatedListener {
    @Autowired
    private FollowService followService;

    @EventListener
    public void onUserCreated(FollowCreatedEvent event) {
        followService.markAsFollowBack(event.getFollow());
    }
}
