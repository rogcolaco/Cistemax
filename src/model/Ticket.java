package model;

public class Ticket {

    private int id;
    private double value;
    private String type;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double ApplyDiscount(){
        return this.getValue() * 0.5;
    }

    public Ticket(int id, double value, String type) {
        this.id= id;
        this.value = value;
        this.type = type;
    }

    public Ticket(int id, double value) {
        this.id = id;
        this.value = value;
    }
    public Ticket() {
    }

    @Override
    public String toString() {
        return this.type;
    }
}
