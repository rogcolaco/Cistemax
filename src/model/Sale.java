package model;

import java.util.Objects;

public class Sale {
    int id;
    String date;
    double price;
    String seats;
    int qtdSeatPromotional;
    double totalSale;
    int sessionId;
    Session session;

    public Sale() {

    }

    public Sale(int id, String seats, int session) {
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

    public Sale(int id, String date, double price, String seats, int qtdSeatPromotional, double totalSale, int sessionId, Session session) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.seats = seats;
        this.qtdSeatPromotional = qtdSeatPromotional;
        this.totalSale = totalSale;
        this.sessionId = sessionId;
        this.session = session;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return id == sale.id &&
                Double.compare(sale.price, price) == 0 &&
                qtdSeatPromotional == sale.qtdSeatPromotional &&
                Double.compare(sale.totalSale, totalSale) == 0 &&
                sessionId == sale.sessionId &&
                date.equals(sale.date) &&
                seats.equals(sale.seats) &&
                session.equals(sale.session);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, price, seats, qtdSeatPromotional, totalSale, sessionId, session);
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
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

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
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

    @Override
    public String toString() {
        return this.date;
    }
}
