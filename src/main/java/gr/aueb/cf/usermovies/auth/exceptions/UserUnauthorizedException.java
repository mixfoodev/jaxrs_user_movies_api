package gr.aueb.cf.usermovies.auth.exceptions;

public class UserUnauthorizedException extends Exception{
    private final static long serialVersionUID = 1L;

    public UserUnauthorizedException(){
        super("User is not authorized!");
    }
}
