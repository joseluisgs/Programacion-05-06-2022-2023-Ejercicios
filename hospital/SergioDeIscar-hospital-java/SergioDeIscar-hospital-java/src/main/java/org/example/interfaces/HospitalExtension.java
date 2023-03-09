package org.example.interfaces;

import org.example.enums.TipoPaciente;
import org.example.models.Paciente;

import java.util.List;

public interface HospitalExtension extends CrudRepository<Paciente, String> {
    Boolean estaCompleto();
    int numPacientes();

    List<Paciente> pacientesOrdeFechaIngreso();
    List<Paciente> pacientesOrdeNombre();
    List<Paciente> pacientesOrdeDNI();

    List<Paciente> pacientesPorTipo(TipoPaciente tipo);
    int numPacientePorTipo(TipoPaciente tipo);
    int capacidadMaxima();
}
