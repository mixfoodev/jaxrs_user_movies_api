package gr.aueb.cf.usermovies.auth.exceptions;

public class UserInvalidPasswordException extends Exception{
    private final static long serialVersionUID = 1L;

    public UserInvalidPasswordException(){
        super("Invalid user password!");
    }
}
