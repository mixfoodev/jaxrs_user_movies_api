package gr.aueb.cf.usermovies.auth;


import gr.aueb.cf.usermovies.auth.exceptions.TokenExpiredException;
import gr.aueb.cf.usermovies.auth.exceptions.TokenInvalidException;
import gr.aueb.cf.usermovies.auth.exceptions.TokenNotFoundException;
import gr.aueb.cf.usermovies.auth.exceptions.UserInvalidPasswordException;
import gr.aueb.cf.usermovies.dto.UserDTO;
import gr.aueb.cf.usermovies.dto.UserOutDTO;
import gr.aueb.cf.usermovies.model.User;
import gr.aueb.cf.usermovies.service.exceptions.EntityDoesNotExistException;

/**
 * Provides CRUD services for {@link User}
 * and Auth services.
 */
public interface IAuthService {

    /**
     * Logs a {@link User} in by checking if user exists
     * and if the provided password is correct.
     *
     * @param userDTO {@link UserDTO} that carries the user's credential.
     * @return the {@link UserOutDTO} of the logged in {@link User}.
     * @throws EntityDoesNotExistException  if {@link User} does not exist.
     * @throws UserInvalidPasswordException if {@link User#password} does not match with the one in datasource.
     */
    UserOutDTO loginUser(UserDTO userDTO) throws EntityDoesNotExistException, UserInvalidPasswordException;

    /**
     * Returns if a {@link User} is admin or not.
     *
     * @param username the {@link User#username} to be checked.
     * @return true if {@link User} is admin, or false otherwise.
     */
    boolean isAdministrator(String username);

    /**
     * Returns if a {@link User} is admin or not based
     * on the received JWT.
     *
     * @param token the JWT to be checked.
     * @return true if {@link User} is admin, or false otherwise.
     */
    boolean isAdminToken(String token);

    /**
     * Checks if a {@link User#id} is subject of
     * o received JWT or JWT belongs to a user with admin rights.
     *
     * @param token the JWT to be checked.
     * @return true if {@link User#id} provided equals the token's subject
     * or if the token belongs to an admin and false otherwise.
     */
    boolean isAuthorized(String token, Long id);

    /**
     * Checks if a JWT is valid.
     *
     * @param token the JWT to be checked.
     * @return true if the JWT is valid, or false otherwise.
     * @throws TokenExpiredException  if the JWT has expired.
     * @throws TokenNotFoundException if the JWT does not exist.
     * @throws TokenInvalidException  if the JWT has invalid signature.
     */
    String validateToken(String token) throws TokenExpiredException, TokenNotFoundException, TokenInvalidException;

    /**
     * Generates a new JWT based on an old one.
     *
     * @param token the JWT from which the new token will be created.
     * @return the generated token.
     */
    String refreshToken(String token);

    /**
     * Extracts and returns the subject claim
     * of a JWT.
     *
     * @param token the token from which the subject will be extracted.
     * @return the extracted subject claim.
     */
    String getSubject(String token);

    /**
     * Generates a Json Web Token.
     *
     * @param subject the subject claim of the token.
     * @param issuer  the issuer claim of the token.
     * @return the generated token.
     */
    String generateToken(String subject, String issuer);
}
