/*
 * @creator: Oswaldo Montes
 * @date: November 28, 2023
 *
 */
package com.koombea.twitterclone.web.app.models.projections.follow;

import com.koombea.twitterclone.web.app.models.projections.user.NamesWithIdOnly;

public interface FollowerOnly {
    NamesWithIdOnly getFollower();
}
