package HospitalCollections.models;

import java.time.LocalDate;

public class Patient {

    private final String dni;
    private final String name;
    private final TypePatient typePatient;
    private final String dateNac;
    private final String dateEnter;
    private final LocalDate dateExit;

    public Patient(String dni, String name, String dateNac, String dateEnter, TypePatient typePatient) {
        this.dni = dni;
        this.name = name;
        this.dateNac = dateNac;
        this.typePatient = typePatient;
        this.dateEnter = dateEnter;
        this.dateExit = LocalDate.now();
    }


    @Override
    public String toString() {
        return "Patient{" +
                "dni='" + dni + '\'' +
                ", name='" + name + '\'' +
                ", typePatient=" + typePatient +
                ", dateNac='" + dateNac + '\'' +
                ", dateEnter='" + dateEnter + '\'' +
                ", dateExit=" + dateExit +
                '}';
    }

    public String getDni() {
        return this.dni;
    }

    public String getName() {
        return this.name;
    }

    public TypePatient getType() {
        return this.typePatient;
    }

    public String getDateEnter() {
        return this.dateEnter;
    }

    public String getDateNac() {
        return dateNac;
    }

    public LocalDate getDateExit() {
        return dateExit;
    }

    public enum TypePatient {
        NORMAL,
        URGENT
    }
}

