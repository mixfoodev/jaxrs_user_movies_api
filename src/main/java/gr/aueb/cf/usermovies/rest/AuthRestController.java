package gr.aueb.cf.usermovies.rest;

import gr.aueb.cf.usermovies.auth.IAuthService;
import gr.aueb.cf.usermovies.auth.exceptions.UserInvalidPasswordException;
import gr.aueb.cf.usermovies.dto.ErrorDTO;
import gr.aueb.cf.usermovies.dto.SuccessDTO;
import gr.aueb.cf.usermovies.dto.UserDTO;
import gr.aueb.cf.usermovies.dto.UserOutDTO;
import gr.aueb.cf.usermovies.rest.cookie.CookieProvider;
import gr.aueb.cf.usermovies.rest.filter.JWTCookieNeeded;
import gr.aueb.cf.usermovies.service.IUserService;
import gr.aueb.cf.usermovies.service.exceptions.EntityDoesNotExistException;
import gr.aueb.cf.usermovies.validation.Validator;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Path("/auth")
public class AuthRestController {

    @Context
    UriInfo uriInfo;

    @Inject
    private IAuthService authService;

    @Inject
    IUserService userService;

    @Inject
    CookieProvider cookieProvider;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JWTCookieNeeded
    public Response getUser(@CookieParam(CookieProvider.COOKIE_NAME) String token) {
        try {
            Long id = Long.parseLong(authService.getSubject(token));
            UserOutDTO outDTO = userService.getUserById(id);
            outDTO.setIsAdmin(authService.isAdministrator(outDTO.getUsername()));
            return Response.ok().entity(outDTO)
                    .cookie(cookieProvider.createCookie(authService.refreshToken(token)))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO("Invalid token")).build();
        }

    }

    @Path("/refresh")
    @GET
    @JWTCookieNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response refreshSession(@CookieParam(CookieProvider.COOKIE_NAME) String token) {
        try {
            String newToken = authService.refreshToken(token);
            return Response.ok().entity(new SuccessDTO("User session refreshed")).cookie(cookieProvider.createCookie(newToken)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO("Invalid token")).build();
        }
    }

    @Path("/logout")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@CookieParam(CookieProvider.COOKIE_NAME) String token) {
        System.out.println("logout");
        if (token == null)
            return Response.status(Response.Status.UNAUTHORIZED).cookie(cookieProvider.createCookie(null)).build();
        return Response.ok().entity(new SuccessDTO("User logout successfully")).cookie(cookieProvider.createCookie(null)).build();
    }

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserDTO userDTO) {
        try {
            if (!Validator.validate(userDTO))
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid user fields").build();

            UserOutDTO outDTO = authService.loginUser(userDTO);
            System.out.println("login dto");
            System.out.println(outDTO); //todo
            String token = authService.generateToken(String.valueOf(outDTO.getId()), uriInfo.getAbsolutePath().toString());
            return Response.ok(outDTO).cookie(cookieProvider.createCookie(token)).build();

        } catch (EntityDoesNotExistException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO("User does not exist")).build();
        } catch (UserInvalidPasswordException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO("Invalid password")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorDTO("Internal server error")).build();
        }
    }
}
