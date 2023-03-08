package models;

public final class Moto extends Vehiculo {
    private final int cilindrada;

    public Moto(String modelo, int kilometros, int añoMatriculacion, boolean apto, int cilindrada) {
        super(modelo, kilometros, añoMatriculacion, apto);
        this.cilindrada = cilindrada;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    @Override
    public String toString() {
        return "Moto{" +
                "modelo='" + super.getModelo() + '\'' +
                ", kilometros=" + super.getKilometros() +
                ", añoMatriculacion=" + super.getAñoMatriculacion() +
                ", apto=" + super.isApto() +
                ", cilindrada=" + cilindrada +
                '}';
    }
}
