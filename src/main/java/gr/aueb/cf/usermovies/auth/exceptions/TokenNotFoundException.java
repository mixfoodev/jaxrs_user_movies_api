package gr.aueb.cf.usermovies.auth.exceptions;

public class TokenNotFoundException extends Exception{
    private final static long serialVersionUID = 1L;

    public TokenNotFoundException(){
        super("Token not found!");
    }
}
