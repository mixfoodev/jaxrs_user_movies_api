package gr.aueb.cf.usermovies.rest;

import gr.aueb.cf.usermovies.dto.ErrorDTO;
import gr.aueb.cf.usermovies.dto.MovieDTO;
import gr.aueb.cf.usermovies.dto.UserDTO;
import gr.aueb.cf.usermovies.dto.UserOutDTO;
import gr.aueb.cf.usermovies.model.Movie;
import gr.aueb.cf.usermovies.rest.filter.AdminOnly;
import gr.aueb.cf.usermovies.rest.filter.AuthorizationNeeded;
import gr.aueb.cf.usermovies.rest.filter.JWTCookieNeeded;
import gr.aueb.cf.usermovies.service.IUserService;
import gr.aueb.cf.usermovies.service.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.usermovies.service.exceptions.EntityDoesNotExistException;
import gr.aueb.cf.usermovies.validation.Validator;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

@JWTCookieNeeded
@Path("/users")
public class UserRestController {

    @Inject
    private IUserService userService;

    @Context
    UriInfo uriInfo;

    @AdminOnly
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(UserDTO userDTO) {
        try {
            if (!Validator.validate(userDTO)) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO("Invalid user fields")).build();
            }
            UserOutDTO outDTO = userService.insertUser(userDTO);
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            URI path = uriBuilder.path(String.valueOf(outDTO.getId())).build();
            return Response.created(path).entity(outDTO).build();

        } catch (EntityAlreadyExistsException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO("User already exists")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorDTO("Internal server error")).build();
        }
    }

    @AuthorizationNeeded
    @POST
    @Path("/{id}/movies")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMovie(@PathParam("id") Long id, MovieDTO movieDTO) {
        try {
            if (!Validator.validate(movieDTO)) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO("Invalid movie fields")).build();
            }
            Movie movie = userService.addUserMovie(movieDTO, id);
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            URI path = uriBuilder.path(String.valueOf(movie.getId())).build();
            return Response.created(path).entity(movie).build();

        } catch (EntityDoesNotExistException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO(e.getMessage())).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorDTO("Internal server error")).build();
        }
    }

    @AuthorizationNeeded
    @DELETE
    @Path("/{id}/movies/{movieId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMovie(@PathParam("id") Long id, @PathParam("movieId") String movieId) {
        try {
            Movie movie = userService.removeUserMovie(movieId, id);
            return Response.ok().entity(movie).build();

        } catch (EntityDoesNotExistException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO(e.getMessage())).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorDTO("Internal server error")).build();
        }
    }

}

