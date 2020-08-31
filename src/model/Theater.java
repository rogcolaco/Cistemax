package model;

import java.util.Objects;

public class Theater {

    private int id;
    private String name;
    private int qtdSeats;

    public Theater(int id, String name, int qtdSeats) {
        this.id = id;
        this.name = name;
        this.qtdSeats = qtdSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Theater theater = (Theater) o;
        return id == theater.id &&
                qtdSeats == theater.qtdSeats &&
                Objects.equals(name, theater.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, qtdSeats);
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
