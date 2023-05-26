package gr.aueb.cf.usermovies.auth;

import org.mindrot.jbcrypt.BCrypt;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.ext.Provider;

@Provider
@RequestScoped
@Named("hashService")
public class BCryptHashServiceImp implements IHashService {

    @Override
    public String getHashed(String text) {
        int workload = 12;
        String salt = BCrypt.gensalt(workload);
        return BCrypt.hashpw(text, salt);
    }

    @Override
    public boolean isValidHash(String text, String hashed) {
        try {
            return BCrypt.checkpw(text, hashed);
        } catch (IllegalArgumentException e) {
            // if the hashed password is not hashed, for some reason.
            return text.equals(hashed);
        }
    }
}
