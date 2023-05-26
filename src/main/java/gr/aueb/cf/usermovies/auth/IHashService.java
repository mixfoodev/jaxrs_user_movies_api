package gr.aueb.cf.usermovies.auth;

/**
 * Provides hashing services.
 */
public interface IHashService {

    /**
     * Returns a hashed String.
     *
     * @param text the String to be hashed.
     * @return the hashed String.
     */
    String getHashed(String text);

    /**
     * Checks if a String matches a hashed one when hashed.
     *
     * @param text   the String to be checked.
     * @param hashed the hashed String to be compared with.
     * @return true if String matches the hashed, or false otherwise.
     */
    boolean isValidHash(String text, String hashed);
}
