package model;

public class Sale {
    int id;
    String date;
    double price;
    String seats;
    int qtdSeatPromotional;
    double totalSale;
    Session session;
    public Sale() {

    };

    public Sale(double price, String seats, int qtdSeatPromotional, double totalSale, Session session) {
        this.date = java.time.LocalDate.now().toString();
        this.price = price;
        this.seats = seats;
        this.qtdSeatPromotional = qtdSeatPromotional;
        this.totalSale = totalSale;
        this.session = session;
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
