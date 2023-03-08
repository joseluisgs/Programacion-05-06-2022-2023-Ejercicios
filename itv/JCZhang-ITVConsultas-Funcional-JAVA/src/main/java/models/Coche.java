package models;

public class Coche extends Vehicle{
    int numPuertas;

    public Coche(String marca, int fabricationYear, boolean apto, double numKm, int numPuertas) {
        super(marca, fabricationYear, apto, numKm);
        this.marca = marca;
        this.fabricationYear = fabricationYear;
        this.apto = apto;
        this.numKm = numKm;
        this.numPuertas = numPuertas;
    }

    @Override
    public String toString() {
        return "Coche{" +
                "marca='" + marca + '\'' +
                ", fabricationYear=" + fabricationYear +
                ", apto=" + apto +
                ", numKm=" + numKm +
                ", numPuertas=" + numPuertas +
                '}';
    }

    public int getNumPuertas() {
        return numPuertas;
    }
}
