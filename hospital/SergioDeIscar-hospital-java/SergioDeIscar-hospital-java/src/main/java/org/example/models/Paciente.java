package org.example.models;

import org.example.enums.TipoPaciente;

import java.time.LocalDate;

public final class Paciente {
    private final String dni;
    private final String nombre;
    private final LocalDate fechaNacimiento;
    private final TipoPaciente tipo;


    private LocalDate fechaIngreso = null;
    private LocalDate fechaAlta = null;

    public Paciente(String dni, String nombre, LocalDate fechaNacimiento, TipoPaciente tipo) {
        this.dni = dni;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.tipo = tipo;
    }
    public Paciente(String dni, String nombre, TipoPaciente tipo) {
        this.dni = dni;
        this.nombre = nombre;
        this.fechaNacimiento = LocalDate.now();
        this.tipo = tipo;
    }
    public String getDni() {
        return dni;
    }
    public String getNombre() {
        return nombre;
    }
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public TipoPaciente getTipo() {
        return tipo;
    }
    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }
    public LocalDate getFechaAlta() {
        return fechaAlta;
    }
    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    @Override
    public int hashCode() {
        return dni.hashCode() + nombre.hashCode() + tipo.hashCode() + fechaNacimiento.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Paciente &&
                ((Paciente) obj).getDni().equals(dni) &&
                ((Paciente) obj).getNombre().equals(nombre) &&
                ((Paciente) obj).getTipo().equals(tipo) &&
                ((Paciente) obj).getFechaNacimiento().equals(fechaNacimiento);
    }
}
