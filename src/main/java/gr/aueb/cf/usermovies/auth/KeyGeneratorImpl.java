package gr.aueb.cf.usermovies.auth;

import gr.aueb.cf.usermovies.MoviesApplication;
import io.jsonwebtoken.security.Keys;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Named;
import javax.ws.rs.ext.Provider;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Provider
@ApplicationScoped
@Default
@Named("keyGenerator")
public class KeyGeneratorImpl implements IKeyGenerator {

    private String SECRET;

    @Override
    public Key generateKey() {
        if (SECRET == null) SECRET = MoviesApplication.getAppicationProperties().getProperty("JWT_SECRET");
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }
}
