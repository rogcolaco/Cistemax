package model;

public class Session {
    private boolean promotional;
    private int id, movie, theather;
    private String starts, ends, seats;

    public Session(){
    }

    public Session(boolean promotional, int id, int movie, int theather, String starts, String ends, String seats) {
        this.promotional = promotional;
        this.id = id;
        this.movie = movie;
        this.theather = theather;
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

    public int getTheather() {
        return theather;
    }

    public void setTheather(int theather) {
        this.theather = theather;
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
