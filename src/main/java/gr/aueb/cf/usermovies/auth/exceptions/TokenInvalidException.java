package gr.aueb.cf.usermovies.auth.exceptions;

public class TokenInvalidException extends Exception{
    private final static long serialVersionUID = 1L;

    public TokenInvalidException(){
        super("Token is not valid!");
    }
}
