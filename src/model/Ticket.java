package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Ticket {

    private double value;
    private String type;
    private ObservableList<Ticket> obsTicket = FXCollections.observableArrayList();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Ticket(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double DiscountApply(){
        return this.getValue() * 0.5;
    }

    public Ticket(double value, String type) {
        this.value = value;
        this.type = type;
    }

    public Ticket() {
    }

    public ObservableList<Ticket> loadTable(){
        //ObservableList<Ticket> obsTicket = FXCollections.observableArrayList();
        //Ticket t = new Ticket(this.getValue(),this.getType());
        //obsTicket.add(t);
        return obsTicket;
    }

    public void addTicket(Ticket t){
        obsTicket.add(t);
        //return obsTicket;
    }

    public void removeTicket(Ticket t){
        obsTicket.remove(t);
    }

}
