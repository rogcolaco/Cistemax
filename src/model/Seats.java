package model;

public class Seats {

    public Seats() {
        this.number = number;
        this.availability = availability;
    }

    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    private boolean availability;

}
