package org.example.controllers;

import org.example.enums.TipoPaciente;
import org.example.exceptions.HospitalExceptions;
import org.example.interfaces.HospitalExtension;
import org.example.models.Paciente;

import java.util.List;

public class HospitalController implements HospitalExtension {
    private final HospitalExtension repo;
    public HospitalController(HospitalExtension repo) {
        this.repo = repo;
    }

    @Override
    public Paciente save(Paciente entity) {
        checkDNI(entity.getDni());
        if (repo.estaCompleto()) { throw new HospitalExceptions.HospitalFullException(); }
        return repo.save(entity);
    }

    @Override
    public Paciente findById(String s) {
        checkDNI(s);
        return repo.findById(s);
    }

    @Override
    public Paciente deleteById(String s) {
        checkDNI(s);
        return repo.deleteById(s);
    }

    @Override
    public List<Paciente> findAll() {
        return repo.findAll();
    }

    @Override
    public void saveAll(List<Paciente> entities) {
        entities.forEach(p -> checkDNI(p.getDni()));
        if (repo.numPacientes() + entities.size() > repo.capacidadMaxima()) { throw new HospitalExceptions.HospitalFullException(); }
        repo.saveAll(entities);
    }

    @Override
    public Boolean estaCompleto() {
        return repo.estaCompleto();
    }

    @Override
    public int numPacientes() {
        return repo.numPacientes();
    }

    @Override
    public List<Paciente> pacientesOrdeFechaIngreso() {
        return repo.pacientesOrdeFechaIngreso();
    }

    @Override
    public List<Paciente> pacientesOrdeNombre() {
        return repo.pacientesOrdeNombre();
    }

    @Override
    public List<Paciente> pacientesOrdeDNI() {
        return repo.pacientesOrdeDNI();
    }

    @Override
    public List<Paciente> pacientesPorTipo(TipoPaciente tipo) {
        return repo.pacientesPorTipo(tipo);
    }

    @Override
    public int numPacientePorTipo(TipoPaciente tipo) {
        return repo.numPacientePorTipo(tipo);
    }

    @Override
    public int capacidadMaxima() {
        return repo.capacidadMaxima();
    }

    private void checkDNI(String dni) {
        if (!dni.matches("[0-9]{8}[A-Z]}")) {
            throw new HospitalExceptions.DniNotValidException();
        }
    }
}
