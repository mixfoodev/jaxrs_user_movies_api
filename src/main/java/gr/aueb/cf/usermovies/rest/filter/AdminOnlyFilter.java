package gr.aueb.cf.usermovies.rest.filter;

import gr.aueb.cf.usermovies.auth.IAuthService;
import gr.aueb.cf.usermovies.auth.exceptions.UserNotAdminException;
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
@AdminOnly
@Priority(Priorities.AUTHORIZATION)
public class AdminOnlyFilter implements ContainerRequestFilter {

    @Inject
    IAuthService authService;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        try {
            Cookie cookie = containerRequestContext.getCookies().get(CookieProvider.COOKIE_NAME);
            if (!authService.isAdminToken(cookie.getValue())) throw new UserNotAdminException();
        } catch (UserNotAdminException e) {
            System.out.println("AdminOnlyFilter: User not an admin.");
            e.printStackTrace();
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        } catch (Exception e) {
            System.out.println("AdminOnlyFilter: Some error occurred.");
            e.printStackTrace();
            containerRequestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
        }
    }
}
