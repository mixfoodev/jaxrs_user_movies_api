package gr.aueb.cf.usermovies.auth.exceptions;

public class TokenExpiredException extends Exception{
    private final static long serialVersionUID = 1L;

    public TokenExpiredException(){
        super("Token expired!");
    }
}
