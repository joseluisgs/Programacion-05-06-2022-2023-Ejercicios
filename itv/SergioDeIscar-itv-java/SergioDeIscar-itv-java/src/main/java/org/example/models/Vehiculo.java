package org.example.models;

import java.time.LocalDate;

public abstract class Vehiculo {
    private final String matricula;
    private final String marca;
    private final LocalDate anio;
    private final boolean apto;
    private final int kilometro;

    public Vehiculo(String matricula, String marca, LocalDate anio, boolean apto, int kilometro) {
        this.matricula = matricula;
        this.marca = marca;
        this.anio = anio;
        this.apto = apto;
        this.kilometro = kilometro;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getMarca() {
        return marca;
    }

    public LocalDate getAnio() {
        return anio;
    }

    public boolean isApto() {
        return apto;
    }

    public int getKilometro() {
        return kilometro;
    }
}

