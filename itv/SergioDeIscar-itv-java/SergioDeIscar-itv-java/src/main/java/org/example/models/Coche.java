package org.example.models;

import java.time.LocalDate;

public class Coche extends Vehiculo {
    private final int numPuertas;

    public Coche(String matricula, String marca, LocalDate anio, boolean apto, int kilometro, int numPuertas) {
        super(matricula, marca, anio, apto, kilometro);
        this.numPuertas = numPuertas;
    }

    public int getNumPuertas() {
        return numPuertas;
    }
}
