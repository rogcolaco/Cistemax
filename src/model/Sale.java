package model;

public class Sale {
    int id;
    String date;
    double price;
    String seats;
    int qtdSeatPromotional;
    double totalSale;
    int sessionId;

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    Session session;
    public Sale() {

    };

    public Sale(int id, String seats, int session){
        this.id = id;
        this.seats = seats;
        this.sessionId = session;
    }

    public Sale(double price, String seats, int qtdSeatPromotional, double totalSale, Session session) {
        this.date = java.time.LocalDate.now().toString();
        this.price = price;
        this.seats = seats;
        this.qtdSeatPromotional = qtdSeatPromotional;
        this.totalSale = totalSale;
        this.session = session;
    }

    public Sale(int id, String date, double price, String seats, int qtdSeatPromotional, double totalSale, int sessionId) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.seats = seats;
        this.qtdSeatPromotional = qtdSeatPromotional;
        this.totalSale = totalSale;
        this.sessionId = sessionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(int qtdSeat) {
        this.seats = seats;
    }

    public int getQtdSeatPromotional() {
        return qtdSeatPromotional;
    }

    public void setQtdSeatPromotional(int qtdSeatPromotional) {
        this.qtdSeatPromotional = qtdSeatPromotional;
    }

    public double getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(double totalSale) {
        this.totalSale = totalSale;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
