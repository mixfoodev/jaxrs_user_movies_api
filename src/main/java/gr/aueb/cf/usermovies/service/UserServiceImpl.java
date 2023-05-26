package gr.aueb.cf.usermovies.service;

import gr.aueb.cf.usermovies.auth.IHashService;
import gr.aueb.cf.usermovies.dao.IMovieDAO;
import gr.aueb.cf.usermovies.dao.IUserDAO;
import gr.aueb.cf.usermovies.dto.MovieDTO;
import gr.aueb.cf.usermovies.dto.UserDTO;
import gr.aueb.cf.usermovies.dto.UserOutDTO;
import gr.aueb.cf.usermovies.model.Movie;
import gr.aueb.cf.usermovies.model.User;
import gr.aueb.cf.usermovies.service.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.usermovies.service.exceptions.EntityDoesNotExistException;
import gr.aueb.cf.usermovies.service.util.JPAHelper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.ext.Provider;

@Provider
@RequestScoped
@Named("userService")
public class UserServiceImpl implements IUserService {

    @Inject
    private IUserDAO userDAO;

    @Inject
    private IMovieDAO movieDAO;

    @Inject
    private IHashService hashService;

    @Override
    public UserOutDTO insertUser(UserDTO userDTO) throws EntityAlreadyExistsException {
        try {
            JPAHelper.beginTransaction();

            if ((userDTO.getId() != null && userDAO.getById(userDTO.getId()) != null)
                    || userDAO.getByUsername(userDTO.getUsername()) != null)
                throw new EntityAlreadyExistsException(User.class);

            userDTO.setPassword(hashService.getHashed(userDTO.getPassword()));
            User user = userDAO.insert(userDTO.toUser());
            JPAHelper.commitTransaction();
            return UserOutDTO.fromUser(user);

        } catch (Exception e) {
            if (!(e instanceof EntityAlreadyExistsException)) e.printStackTrace();
            JPAHelper.rollbackTransaction();
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public UserOutDTO getUserById(Long id) throws EntityDoesNotExistException {
        try {
            JPAHelper.beginTransaction();

            User user = userDAO.getById(id);
            if (user == null) throw new EntityDoesNotExistException(User.class);

            UserOutDTO outDTO = UserOutDTO.fromUser(user);
            outDTO.setMovies(user.getMovies()); // fetch user movies lazily

            JPAHelper.commitTransaction();
            return outDTO;
        } catch (Exception e) {
            JPAHelper.rollbackTransaction();
            e.printStackTrace();
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public UserOutDTO updateUser(UserDTO userDTO) throws EntityDoesNotExistException {

        try {
            JPAHelper.beginTransaction();
            if (userDTO.getId() == null || userDAO.getById(userDTO.getId()) == null)
                throw new EntityDoesNotExistException(User.class);

            userDTO.setPassword(hashService.getHashed(userDTO.getPassword()));
            User user = userDAO.update(userDTO.toUser());

            JPAHelper.commitTransaction();
            return UserOutDTO.fromUser(user);
        } catch (Exception e) {
            if (!(e instanceof EntityDoesNotExistException)) e.printStackTrace();
            JPAHelper.rollbackTransaction();
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public UserOutDTO deleteUser(Long id) throws EntityDoesNotExistException {
        if (id < 1) throw new EntityDoesNotExistException(User.class);

        try {
            JPAHelper.beginTransaction();
            User user = userDAO.delete(id);
            if (user == null) throw new EntityDoesNotExistException(User.class);

            JPAHelper.commitTransaction();
            return UserOutDTO.fromUser(user);
        } catch (Exception e) {
            if (!(e instanceof EntityDoesNotExistException)) e.printStackTrace();
            JPAHelper.rollbackTransaction();
            e.printStackTrace();
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }


    @Override
    public Movie addUserMovie(MovieDTO movieDTO, Long userId) throws EntityDoesNotExistException {
        try {
            JPAHelper.beginTransaction();
            User user = userDAO.getById(userId);
            if (user == null) throw new EntityDoesNotExistException(User.class);
            Movie movie = movieDAO.getMovieById(movieDTO.getId()); //movieFromDTO(movieDTO);
            if (movie == null) movie = movieDAO.insert(movieDTO.toMovie());

            user.addMovie(movie);
            userDAO.insert(user);
            JPAHelper.commitTransaction();
            return movie;
        } catch (Exception e) {
            JPAHelper.rollbackTransaction();
            e.printStackTrace();
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public Movie removeUserMovie(String movieId, Long userId) throws EntityDoesNotExistException {
        try {
            JPAHelper.beginTransaction();
            User user = userDAO.getById(userId);
            if (user == null) throw new EntityDoesNotExistException(User.class);
            Movie movie = movieDAO.getMovieById(movieId);
            if (movie == null) throw new EntityDoesNotExistException(Movie.class);

            user.removeMovie(movie);
            userDAO.insert(user);
            JPAHelper.commitTransaction();
            return movie;
        } catch (Exception e) {
            JPAHelper.rollbackTransaction();
            e.printStackTrace();
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }
}

