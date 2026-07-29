package sample.common.logic;

import jakarta.servlet.http.HttpSession;

public final class SessionKeys {
    public static final String USERNAME = "username";

    private SessionKeys() {
    }

    public static String currentUsername(HttpSession session) {
        return (String) session.getAttribute(USERNAME);
    }
}
