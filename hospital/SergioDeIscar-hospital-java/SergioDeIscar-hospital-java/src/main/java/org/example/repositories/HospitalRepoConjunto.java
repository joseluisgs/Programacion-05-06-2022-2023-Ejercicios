package org.example.repositories;

import org.example.enums.TipoPaciente;
import org.example.interfaces.HospitalExtension;
import org.example.models.Paciente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class HospitalRepoConjunto implements HospitalExtension {
    private final int maxSize;
    public HospitalRepoConjunto() {
        this.maxSize = 50;
    }
    public HospitalRepoConjunto(int maxSize) {
        this.maxSize = maxSize;
    }

    Set<Paciente> pacientes = new LinkedHashSet<>();

    @Override
    public Paciente save(Paciente entity) {
        if (estaCompleto() || entity == null) return null;
        entity.setFechaIngreso(LocalDate.now());
        pacientes.add(entity);
        return entity;
    }

    @Override
    public Paciente findById(String s) {
        return pacientes.stream().filter(p -> p.getDni().equals(s)).findFirst().orElse(null);
    }

    @Override
    public Paciente deleteById(String s) {
        Paciente paciente = findById(s);
        if (paciente == null) return null;
        paciente.setFechaAlta(LocalDate.now());
        pacientes.remove(paciente);
        return paciente;
    }

    @Override
    public List<Paciente> findAll() {
        return pacientes.stream().toList();
    }

    @Override
    public void saveAll(List<Paciente> entities) {
        if (estaCompleto()) return;
        entities.forEach(p -> save(p));
    }

    @Override
    public Boolean estaCompleto() {
        return pacientes.size() >= maxSize;
    }

    @Override
    public int numPacientes() {
        return pacientes.size();
    }

    @Override
    public List<Paciente> pacientesOrdeFechaIngreso() {
        return pacientes.stream().sorted((p1, p2) -> p1.getFechaIngreso().compareTo(p2.getFechaIngreso())).toList();
    }

    @Override
    public List<Paciente> pacientesOrdeNombre() {
        return pacientes.stream().sorted((p1, p2) -> p1.getNombre().compareTo(p2.getNombre())).toList();
    }

    @Override
    public List<Paciente> pacientesOrdeDNI() {
        return pacientes.stream().sorted((p1, p2) -> p1.getDni().compareTo(p2.getDni())).toList();
    }

    @Override
    public List<Paciente> pacientesPorTipo(TipoPaciente tipo) {
        return pacientes.stream().filter(p -> p.getTipo().equals(tipo)).toList();
    }

    @Override
    public int numPacientePorTipo(TipoPaciente tipo) {
        return pacientesPorTipo(tipo).size();
    }

    @Override
    public int capacidadMaxima() {
        return maxSize;
    }
}
