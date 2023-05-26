package gr.aueb.cf.usermovies.dto;

import gr.aueb.cf.usermovies.model.Movie;

public class MovieDTO {

    private String id;

    private String title;

    private String poster;

    private String year;

    public MovieDTO() {
    }

    public MovieDTO(String id, String title, String poster, String year) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", poster='" + poster + '\'' +
                ", year=" + year +
                '}';
    }

    // Mappers
    public static MovieDTO fromMovie(Movie movie){
        return new MovieDTO(movie.getId(), movie.getTitle(), movie.getPoster(), movie.getYear());
    }

    public Movie toMovie(){
        return new Movie(this.id, this.title, this.poster, this.year);
    }
}
