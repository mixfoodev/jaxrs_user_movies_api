package gr.aueb.cf.usermovies.rest.cookie;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.ext.Provider;

@ApplicationScoped
@Provider
public class CookieProvider {

    public static final String COOKIE_NAME = "x-auth-token";

    public NewCookie createCookie(String token) {
        Cookie cookie = new Cookie(COOKIE_NAME, token, "/", "");
        int maxAge = token == null ? 0 : 3600 * 24;
        return new NewCookie(cookie, "", maxAge, false);
    }
}
