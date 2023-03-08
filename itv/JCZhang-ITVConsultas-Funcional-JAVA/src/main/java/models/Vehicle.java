package models;

public abstract class Vehicle {
    String marca;
    int fabricationYear;
    boolean apto;
    double numKm;

    public Vehicle(String marca, int fabricationYear, boolean apto, double numKm){
        this.marca = marca;
        this.fabricationYear = fabricationYear;
        this.apto = apto;
        this.numKm = numKm;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "marca='" + marca + '\'' +
                ", fabricationYear=" + fabricationYear +
                ", apto=" + apto +
                ", numKm=" + numKm +
                '}';
    }

    public String getMarca() {
        return marca;
    }

    public Vehicle setMarca(String marca) {
        this.marca = marca;
        return this;
    }

    public int getFabricationYear() {
        return fabricationYear;
    }

    public Vehicle setFabricationYear(int fabricationYear) {
        this.fabricationYear = fabricationYear;
        return this;
    }

    public boolean isApto() {
        return apto;
    }

    public Vehicle setApto(boolean apto) {
        this.apto = apto;
        return this;
    }

    public double getNumKm() {
        return numKm;
    }

    public Vehicle setNumKm(double numKm) {
        this.numKm = numKm;
        return this;
    }
}
