package model;

public class Session {
    private boolean promotional;
    private int id;
    private int movie;
    private int theater;
    private int ticket;
    private String starts;
    private String ends;
    private String seats;
    private String date;

    public Session(){
    }

    public Session(int theater, String starts, String ends, boolean promotional) {
        this.theater = theater;
        this.starts = starts;
        this.ends = ends;
        this.promotional = promotional;
    }

    public Session(boolean promotional, int id, int movie, int theater, int ticket, String starts, String ends, String seats, String date) {
        this.promotional = promotional;
        this.id = id;
        this.movie = movie;
        this.theater = theater;
        this.starts = starts;
        this.ends = ends;
        this.seats = seats;
        this.date = date;
        this.ticket = ticket;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTicket() { return ticket; }

    public void setTicket(int ticket) { this.ticket = ticket; }

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

    public void setTheater(int theater) {
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
