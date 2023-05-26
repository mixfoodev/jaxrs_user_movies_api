package gr.aueb.cf.usermovies.rest.filter;

import gr.aueb.cf.usermovies.auth.IAuthService;
import gr.aueb.cf.usermovies.auth.exceptions.UserUnauthorizedException;
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
@AuthorizationNeeded
@Priority(Priorities.AUTHORIZATION)
public class AuthorizedFilter implements ContainerRequestFilter {

    @Inject
    IAuthService authService;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        try {
            Cookie cookie = containerRequestContext.getCookies().get(CookieProvider.COOKIE_NAME);
            Long userId = Long.valueOf(containerRequestContext.getUriInfo().getPathParameters().get("id").get(0));
            if (!authService.isAuthorized(cookie.getValue(), userId)) throw new UserUnauthorizedException();

        } catch (UserUnauthorizedException e) {
            System.out.println("AuthorizedFilter: Not authorized");
            e.printStackTrace();
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        } catch (Exception e) {
            System.out.println("AuthorizedFilter: Some error occurred.");
            e.printStackTrace();
            containerRequestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
        }

    }
}
