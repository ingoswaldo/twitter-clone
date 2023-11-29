/*
 * @creator: Oswaldo Montes
 * @date: November 29, 2023
 *
 */
package com.koombea.twitterclone.web.app.shared.utilities;

public final class StringUtils {
    public static String humanizeText(String text) {
        return text.substring(0,1).toUpperCase().concat(text.substring(1));
    }
}
