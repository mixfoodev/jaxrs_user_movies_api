package gr.aueb.cf.usermovies.validation;

import gr.aueb.cf.usermovies.dto.MovieDTO;
import gr.aueb.cf.usermovies.dto.UserDTO;

/**
 * Demo validation class that provides
 * a simple DTO validation.
 */
public class Validator {

    private Validator() {
    }

    public static boolean validate(MovieDTO dto) {
        return dto != null && !dto.getTitle().equals("") && !dto.getId().equals("");
    }

    public static boolean validate(UserDTO dto) {
        return dto != null && !dto.getUsername().equals("") && !dto.getPassword().equals("");
    }
}
