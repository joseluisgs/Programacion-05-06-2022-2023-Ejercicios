package models;

public final class Coche extends Vehiculo {
    private final int numeroPuertas;

    public Coche(String modelo, int kilometros, int añoMatriculacion, boolean apto, int numeroPuertas) {
        super(modelo, kilometros, añoMatriculacion, apto);
        this.numeroPuertas = numeroPuertas;
    }

    public int getNumeroPuertas() {
        return numeroPuertas;
    }

    @Override
    public String toString() {
        return "Coche{" +
                "modelo='" + super.getModelo() + '\'' +
                ", kilometros=" + super.getKilometros() +
                ", añoMatriculacion=" + super.getAñoMatriculacion() +
                ", apto=" + super.isApto() +
                ", numeroPuertas=" + numeroPuertas +
                '}';
    }
}
