package gr.aueb.cf.usermovies.dto;

import gr.aueb.cf.usermovies.model.Movie;
import gr.aueb.cf.usermovies.model.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserOutDTO {
    private Long id;
    private String username;
    private boolean isAdmin = false;

    private Set<MovieDTO> movies = new HashSet<>();

    public UserOutDTO() {
    }

    public UserOutDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<MovieDTO> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies.stream().map(MovieDTO::fromMovie).collect(Collectors.toSet());
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "UserOutDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", movies=" + movies +
                '}';
    }

    // Mappers
    public static UserOutDTO fromUser(User user){
        return new UserOutDTO(user.getId(), user.getUsername());
    }
}
