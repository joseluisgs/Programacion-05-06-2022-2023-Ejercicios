package FunctionalITVJava.models;

public class Moto extends Vehicle {
    public final int cilindra;

    public Moto(String brand, int anyo, Boolean apto, int km, int cilindra) {
        super(brand, anyo, apto, km);
        this.cilindra = cilindra;
    }

    @Override
    public String toString() {
        return "Moto(brand='" + brand + "', anyo=" + anyo + ", apto=" + apto + ", km=" + km + ", cilindra=" + cilindra + ")";
    }
}
