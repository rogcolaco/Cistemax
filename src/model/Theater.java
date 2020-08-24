package model;

public class Theater {

    private int id;
    private String name;
    private int qtdSeats;

    public Theater(int id, String name, int qtdSeats) {
        this.id = id;
        this.name = name;
        this.qtdSeats = qtdSeats;
    }

    public Theater() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQtdSeats() {
        return qtdSeats;
    }

    public void setQtdSeats(int qtdSeats) {
        this.qtdSeats = qtdSeats;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
