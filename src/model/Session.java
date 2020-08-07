package model;

public class Session {
    private boolean promotional;
    private int id, movie, theater;
    private String starts, ends, seats;
    public Session(int theater, String starts, String ends, boolean promotional) {
        this.theater = theater;
        this.starts = starts;
        this.ends = ends;
        this.promotional = promotional;
    }

    public Session(){
    }

    public Session(boolean promotional, int id, int movie, int theater, String starts, String ends, String seats) {
        this.promotional = promotional;
        this.id = id;
        this.movie = movie;
        this.theater = theater;
        this.starts = starts;
        this.ends = ends;
        this.seats = seats;
    }

    public boolean isPromotional() {
        return promotional;
    }

    public void setPromotional(boolean promotional) {
        this.promotional = promotional;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovie() {
        return movie;
    }

    public void setMovie(int movie) {
        this.movie = movie;
    }

    public int getTheater() {
        return theater;
    }

    public void setTheater(int theather) {
        this.theater = theater;
    }

    public String getStarts() {
        return starts;
    }

    public void setStarts(String starts) {
        this.starts = starts;
    }

    public String getEnds() {
        return ends;
    }

    public void setEnds(String ends) {
        this.ends = ends;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }
}
