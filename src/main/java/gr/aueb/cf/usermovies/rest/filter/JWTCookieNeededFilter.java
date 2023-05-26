package gr.aueb.cf.usermovies.rest.filter;

import gr.aueb.cf.usermovies.auth.IAuthService;
import gr.aueb.cf.usermovies.auth.exceptions.TokenExpiredException;
import gr.aueb.cf.usermovies.auth.exceptions.TokenInvalidException;
import gr.aueb.cf.usermovies.auth.exceptions.TokenNotFoundException;
import gr.aueb.cf.usermovies.rest.cookie.CookieProvider;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@JWTCookieNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTCookieNeededFilter implements ContainerRequestFilter {

    @Inject
    IAuthService authService;

    @Inject
    CookieProvider cookieService;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        try{
            Cookie cookie = containerRequestContext.getCookies().get(CookieProvider.COOKIE_NAME);
            if (cookie == null) throw new TokenNotFoundException();
            authService.validateToken(cookie.getValue());
        }catch (TokenExpiredException e){
            System.out.println("JWTCookieNeededFilter: Token expired");
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).cookie(cookieService.createCookie(null)).build());
            e.printStackTrace();
        }catch (TokenNotFoundException e){
            System.out.println("JWTCookieNeededFilter: Token not found");
            containerRequestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).build());
            e.printStackTrace();
        }catch (TokenInvalidException e){
            System.out.println("JWTCookieNeededFilter: Invalid Token");
            containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
            e.printStackTrace();
        }
    }
}
