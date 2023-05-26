package gr.aueb.cf.usermovies.auth;

import java.security.Key;

/**
 * Provides Secret keys for signing JWT.
 */
public interface IKeyGenerator {

    /**
     * Generates a hashed key.
     *
     * @return the generated key.
     */
    Key generateKey();
}
