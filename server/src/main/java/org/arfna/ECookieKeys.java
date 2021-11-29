package org.arfna;

public enum ECookieKeys {

    SUBSCRIBER("subscriber");

    private String cookieName;

    ECookieKeys(String cookieName) {
        this.cookieName = cookieName;
    }

    public String getCookieName() {
        return cookieName;
    }
}
