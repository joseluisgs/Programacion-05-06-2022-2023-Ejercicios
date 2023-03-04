package FunctionalITVJava.models;

public abstract class Vehicle {
    public final int anyo;
    public final int km;
    public final String brand;
    public final Boolean apto;

    Vehicle(String brand, int anyo, Boolean apto, int km) {
        this.brand = brand;
        this.anyo = anyo;
        this.apto = apto;
        this.km = km;
    }
}