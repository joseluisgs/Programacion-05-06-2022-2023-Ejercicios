package org.example.models;

import java.time.LocalDate;

public class Moto extends Vehiculo {
    private final int cilindrada;

    public Moto(String matricula, String marca, LocalDate anio, boolean apto, int kilometro, int cilindrada) {
        super(matricula, marca, anio, apto, kilometro);
        this.cilindrada = cilindrada;
    }

    public int getCilindrada() {
        return cilindrada;
    }
}
