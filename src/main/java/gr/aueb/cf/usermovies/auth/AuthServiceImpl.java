package gr.aueb.cf.usermovies.auth;


import gr.aueb.cf.usermovies.auth.exceptions.TokenExpiredException;
import gr.aueb.cf.usermovies.auth.exceptions.TokenInvalidException;
import gr.aueb.cf.usermovies.auth.exceptions.TokenNotFoundException;
import gr.aueb.cf.usermovies.auth.exceptions.UserInvalidPasswordException;
import gr.aueb.cf.usermovies.dao.IUserDAO;
import gr.aueb.cf.usermovies.dto.UserDTO;
import gr.aueb.cf.usermovies.dto.UserOutDTO;
import gr.aueb.cf.usermovies.model.User;
import gr.aueb.cf.usermovies.service.exceptions.EntityDoesNotExistException;
import gr.aueb.cf.usermovies.service.util.JPAHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.ext.Provider;

@Provider
@RequestScoped
@Named("authService")
public class AuthServiceImpl implements IAuthService {

    @Inject
    private IUserDAO userDAO;

    @Inject
    private IHashService hashService;

    @Inject
    private JwtService jwtService;

    @Override
    public UserOutDTO loginUser(UserDTO userDTO) throws EntityDoesNotExistException, UserInvalidPasswordException {
        try {
            JPAHelper.beginTransaction();
            User user = userDAO.getByUsername(userDTO.getUsername());
            if (user == null) {
                if (isAdmin(userDTO.getUsername())) user = createAdmin();
                else throw new EntityDoesNotExistException(User.class);
            }
            if (!hashService.isValidHash(userDTO.getPassword(), user.getPassword()))
                throw new UserInvalidPasswordException();
            UserOutDTO outDTO = UserOutDTO.fromUser(user);
            outDTO.setMovies(user.getMovies());
            outDTO.setIsAdmin(isAdmin(outDTO.getUsername()));
            JPAHelper.commitTransaction();
            return outDTO;

        } catch (Exception e) {
            System.out.println("Error. Could not log in user.");
            e.printStackTrace();
            JPAHelper.rollbackTransaction();
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public boolean isAuthorized(String token, Long id) {
        try {
            Long tokenId = Long.parseLong(jwtService.extractSubject(token));
            User user = userDAO.getById(tokenId);
            return tokenId.equals(id) || isAdmin(user.getUsername());
        } catch (Exception e) {
            System.out.println("Error. Invalid token");
            return false;
        }
    }

    // demo admin check
    @Override
    public boolean isAdministrator(String username) {
        return isAdmin(username);
    }

    @Override
    public boolean isAdminToken(String token) {
        try {
            Long id = Long.parseLong(jwtService.extractSubject(token));
            User user = userDAO.getById(id);
            return isAdmin(user.getUsername());
        } catch (Exception e) {
            System.out.println("Error. Invalid token");
            return false;
        }
    }

    @Override
    public String validateToken(String token) throws TokenExpiredException, TokenNotFoundException, TokenInvalidException {
        if (token == null) throw new TokenNotFoundException();

        try {
            return jwtService.extractSubject(token);
        } catch (ExpiredJwtException e) {
            System.out.println(e.getMessage());
            throw new TokenExpiredException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new TokenInvalidException();
        }
    }

    @Override
    public String getSubject(String token) {
        return jwtService.extractSubject(token);
    }

    @Override
    public String generateToken(String subject, String issuer) {
        return jwtService.createToken(subject, issuer);
    }

    @Override
    public String refreshToken(String token) {
        Claims claims = jwtService.extractAllClaims(token);
        String subject = claims.getSubject();
        String issuer = claims.getIssuer();
        return jwtService.createToken(subject, issuer);
    }

    private boolean isAdmin(String username) {
        return username.equals("admin");
    }

    // demo admin creation
    private User createAdmin() {
        return userDAO.insert(new User(null, "admin", hashService.getHashed("admin")));
    }
}
