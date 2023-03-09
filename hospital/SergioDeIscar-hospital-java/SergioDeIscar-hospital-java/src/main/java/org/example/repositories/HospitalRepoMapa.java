package org.example.repositories;

import org.example.enums.TipoPaciente;
import org.example.interfaces.HospitalExtension;
import org.example.models.Paciente;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public final class HospitalRepoMapa implements HospitalExtension {
    private final int maxSize;
    public HospitalRepoMapa() {
        this.maxSize = 50;
    }
    public HospitalRepoMapa(int maxSize) {
        this.maxSize = maxSize;
    }

    HashMap<String, Paciente> pacientes = new LinkedHashMap<>();

    @Override
    public Paciente save(Paciente entity) {
        if (estaCompleto()) return null;
        entity.setFechaIngreso(LocalDate.now());
        pacientes.put(entity.getDni(), entity);
        return entity;
    }

    @Override
    public Paciente findById(String s) {
        return pacientes.get(s);
    }

    @Override
    public Paciente deleteById(String s) {
        var paciente = pacientes.get(s);
        if (paciente == null) return null;
        paciente.setFechaAlta(LocalDate.now());
        return pacientes.remove(s) == null ? null : paciente;
    }

    @Override
    public List<Paciente> findAll() {
        return pacientes.values().stream().toList();
    }

    @Override
    public void saveAll(List<Paciente> entities) {
        if (estaCompleto()) return;
        entities.forEach(p -> this.save(p));
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
        return pacientes.values().stream().sorted((p1, p2) -> p1.getFechaIngreso().compareTo(p2.getFechaIngreso())).toList();
    }

    @Override
    public List<Paciente> pacientesOrdeNombre() {
        return pacientes.values().stream().sorted((p1, p2) -> p1.getNombre().compareTo(p2.getNombre())).toList();
    }

    @Override
    public List<Paciente> pacientesOrdeDNI() {
        return pacientes.values().stream().sorted((p1, p2) -> p1.getDni().compareTo(p2.getDni())).toList();
    }

    @Override
    public List<Paciente> pacientesPorTipo(TipoPaciente tipo) {
        return pacientes.values().stream().filter(p -> p.getTipo() == tipo).toList();
    }

    @Override
    public int numPacientePorTipo(TipoPaciente tipo) {
        return (int)(pacientes.values().stream().filter(p -> p.getTipo() == tipo).count());
    }

    @Override
    public int capacidadMaxima() {
        return maxSize;
    }
}
