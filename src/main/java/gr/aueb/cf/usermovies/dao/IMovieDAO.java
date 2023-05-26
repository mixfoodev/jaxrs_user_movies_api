package gr.aueb.cf.usermovies.dao;


import gr.aueb.cf.usermovies.model.Movie;

import java.util.List;

/**
 * Provides CRUD operations for {@link Movie}
 */
public interface IMovieDAO {

    /**
     * Inserts a {@link Movie} into datasource.
     *
     * @param movie the {@link Movie} to be inserted.
     * @return the inserted {@link Movie}.
     */
    Movie insert(Movie movie);

    /**
     * Deletes a {@link Movie} from datasource.
     *
     * @param id the {@link Movie#id} to be deleted.
     * @return the deleted {@link Movie}.
     */
    Movie delete(String id);

    /**
     * Retrieves a {@link Movie} from datasource.
     *
     * @param id the {@link Movie#id} to be retrieved.
     * @return the retrieved {@link Movie}.
     */
    Movie getMovieById(String id);


    /**
     * Retrieves all {@link List}<{@link Movie}> from datasource.
     *
     * @return a {@link List}<{@link Movie}> with all movies from datasource.
     */
    List<Movie> getAllMovies();
}
