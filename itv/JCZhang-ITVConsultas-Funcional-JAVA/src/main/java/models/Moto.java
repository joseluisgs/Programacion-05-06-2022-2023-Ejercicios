package models;

public class Moto extends Vehicle {
    int cilindrada;

    public Moto(String marca, int fabricationYear, boolean apto, double numKm, int cilindrada){
        super(marca, fabricationYear, apto, numKm);
        this.marca = marca;
        this.fabricationYear = fabricationYear;
        this.apto = apto;
        this.numKm = numKm;
        this.cilindrada = cilindrada;
    }

    @Override
    public String toString() {
        return "Moto{" +
                "marca='" + marca + '\'' +
                ", fabricationYear=" + fabricationYear +
                ", apto=" + apto +
                ", numKm=" + numKm +
                ", cilindrada=" + cilindrada +
                '}';
    }

    public int getCilindrada() {
        return cilindrada;
    }
}
