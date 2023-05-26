package gr.aueb.cf.usermovies.service;

import gr.aueb.cf.usermovies.dto.MovieDTO;
import gr.aueb.cf.usermovies.dto.UserDTO;
import gr.aueb.cf.usermovies.dto.UserOutDTO;
import gr.aueb.cf.usermovies.model.Movie;
import gr.aueb.cf.usermovies.model.User;
import gr.aueb.cf.usermovies.service.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.usermovies.service.exceptions.EntityDoesNotExistException;

public interface IUserService {

    /**
     * Creates a {@link User} instance.
     *
     * @param userDTO {@link UserDTO} of the {@link User} to be created.
     * @return {@link UserOutDTO} of the created {@link User}.
     * @throws EntityAlreadyExistsException if user already exists.
     */
    UserOutDTO insertUser(UserDTO userDTO) throws EntityAlreadyExistsException;

    /**
     * Retrieves a {@link User} based on {@link User#id}
     *
     * @param id {@link UserDTO} of the {@link User} to be retrieved.
     * @return {@link UserOutDTO} of the retrieved {@link User}.
     * @throws EntityDoesNotExistException if user does not exist.
     */
    UserOutDTO getUserById(Long id) throws EntityDoesNotExistException;

    /**
     * Updates a {@link User} instance.
     *
     * @param userDTO userDTO {@link UserDTO} of the {@link User} to be updated.
     * @return {@link UserOutDTO} of the updated {@link User}.
     * @throws EntityDoesNotExistException if {@link User} does not exist.
     */
    UserOutDTO updateUser(UserDTO userDTO) throws EntityDoesNotExistException;

    /**
     * Deletes a {@link User} instance.
     *
     * @param id the {@link User#id}.
     * @return {@link UserOutDTO} of the deleted {@link User}.
     * @throws EntityDoesNotExistException if {@link User} does not exist.
     */
    UserOutDTO deleteUser(Long id) throws EntityDoesNotExistException;


    /**
     * Adds a {@link Movie} instance to a {@link User}.
     *
     * @param movieDTO {@link MovieDTO} of the {@link Movie} to be created.
     * @param userId   {@link User#id} of the {@link User} for the movie
     *                 to be associated with.
     * @return {@link MovieDTO} of the created {@link Movie}.
     * @throws EntityDoesNotExistException if user does not exist.
     */
    Movie addUserMovie(MovieDTO movieDTO, Long userId) throws EntityDoesNotExistException;

    /**
     * Removes a {@link Movie} from a {@link User}.
     *
     * @param movieId {@link Movie#id} of the {@link Movie} to be deleted.
     * @param userId  {@link User#id} of the {@link User} for the movie
     *                to be deleted from.
     * @return {@link MovieDTO} of the deleted {@link Movie}.
     * @throws EntityDoesNotExistException if user or movie does not exist.
     */
    Movie removeUserMovie(String movieId, Long userId) throws EntityDoesNotExistException;
}
