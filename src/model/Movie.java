package model;

public class Movie {

    int id, duration;
    String name, director, parentalRating;
    boolean inTheaters;
    Genre genre;

    public Movie() {
    }

    public Movie(int id, int duration, String name, String director, String parentalRating, boolean inTheaters, Genre genre) {
        this.id = id;
        this.duration = duration;
        this.name = name;
        this.director = director;
        this.parentalRating = parentalRating;
        this.inTheaters = inTheaters;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getParentalRating() {
        return parentalRating;
    }

    public void setParentalRating(String parentalRating) {
        this.parentalRating = parentalRating;
    }

    public Boolean getInTheaters() {
        return inTheaters;
    }

    public void setInTheaters(boolean inTheaters) {
        this.inTheaters = inTheaters;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
