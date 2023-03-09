package models;

public class Vehiculo {
    private final String modelo;
    private int kilometros;
    private final int añoMatriculacion;
    private final boolean apto;

    public Vehiculo(String modelo, int kilometros, int añoMatriculacion, boolean apto) {
        this.modelo = modelo;
        this.kilometros = kilometros;
        this.añoMatriculacion = añoMatriculacion;
        this.apto = apto;
    }

    public String getModelo() {
        return modelo;
    }

    public int getKilometros() {
        return kilometros;
    }

    public void setKilometros(int kilometros) {
        this.kilometros = kilometros;
    }

    public int getAñoMatriculacion() {
        return añoMatriculacion;
    }

    public boolean isApto() {
        return apto;
    }
}

