package gr.aueb.cf.usermovies.dao;

import gr.aueb.cf.usermovies.model.Movie;
import gr.aueb.cf.usermovies.service.util.JPAHelper;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
@RequestScoped
@Named("movieDAO")
public class MovieDaoImpl implements IMovieDAO{
    @Override
    public Movie insert(Movie movie) {
        JPAHelper.getEntityManager().persist(movie);
        return movie;
    }

    @Override
    public Movie delete(String id) {
        Movie movie = JPAHelper.getEntityManager().find(Movie.class, id);
        JPAHelper.getEntityManager().remove(movie);
        return movie;
    }

    @Override
    public Movie getMovieById(String id) {
        return JPAHelper.getEntityManager().find(Movie.class, id);
    }

    @Override
    public List<Movie> getAllMovies() {
        CriteriaBuilder builder = JPAHelper.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Movie> selectQuery = builder.createQuery(Movie.class);
        Root<Movie> root = selectQuery.from(Movie.class);

        selectQuery.select(root);

        TypedQuery<Movie> query = JPAHelper.getEntityManager().createQuery(selectQuery);
        return query.getResultList();
    }
}
