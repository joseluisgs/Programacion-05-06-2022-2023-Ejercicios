package FunctionalITVJava.models;

public class Car extends Vehicle {
    public final int numDoors;

    public Car(String brand, int anyo, Boolean apto, int km, int numDoors) {
        super(brand, anyo, apto, km);
        this.numDoors = numDoors;
    }

    @Override
    public String toString() {
        return "Car(brand='" + brand + "', anyo=" + anyo + ", apto=" + apto + ", km=" + km + ", numDoors=" + numDoors + ")";

    }
}
