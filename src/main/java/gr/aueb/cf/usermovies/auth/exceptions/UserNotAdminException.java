package gr.aueb.cf.usermovies.auth.exceptions;

public class UserNotAdminException extends Exception{
    private final static long serialVersionUID = 1L;

    public UserNotAdminException(){
        super("User is not an administrator!");
    }
}
