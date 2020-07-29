package model;

public class Ticket {

    private double value = 0;

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

}
