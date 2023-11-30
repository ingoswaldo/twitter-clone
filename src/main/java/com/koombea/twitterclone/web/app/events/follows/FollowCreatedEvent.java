/*
 * @creator: Oswaldo Montes
 * @date: November 30, 2023
 *
 */
package com.koombea.twitterclone.web.app.events.follows;

import com.koombea.twitterclone.web.app.models.entities.Follow;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class FollowCreatedEvent extends ApplicationEvent {
    private Follow follow;

    public FollowCreatedEvent(Object source, Follow follow) {
        super(source);
        this.follow = follow;
    }
}
