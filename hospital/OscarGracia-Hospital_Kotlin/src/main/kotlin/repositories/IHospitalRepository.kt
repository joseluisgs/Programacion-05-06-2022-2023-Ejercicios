package repositories

import models.Paciente
import models.TipoPaciente

interface IHospitalRepository {
    fun ingresar(paciente: Paciente)
    fun alta(paciente: Paciente,fechaAlta:String): Paciente?
    fun estaCompleto(): Boolean
    fun numeroPacientes(): Int
    fun obtenertodosPacientes(): Any
    fun pacientePorDni(dni:String): Paciente?
    fun pacientesOrdenadosFechaIngreso(): Any
    fun pacientesOrdenadosPorNombre(): Any
    fun pacientesPorTipo(tipoPaciente: TipoPaciente): Any
    fun numPacientesPorTipo(tipoPaciente: TipoPaciente): Int
    fun borrarpaciente(paciente: Paciente)
}