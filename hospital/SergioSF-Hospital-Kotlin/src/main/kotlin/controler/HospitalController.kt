package controler

import exceptions.OpcionInvalida
import exceptions.OptionNull
import models.Pacientes
import repositories.hospitalRepositoryConjuntos
import repositories.hospitalRepositoryListas
import repositories.hospitalRepositoryMapas

class HospitalController(
    private val hospitalRepositoryListas: hospitalRepositoryListas,
    private val hospitalRepositoryConjuntos: hospitalRepositoryConjuntos,
    private val hospitalRepositoryMapas: hospitalRepositoryMapas
) {

    fun isCompletoListas(): Boolean {
        return hospitalRepositoryListas.isCompletoListas()
    }

    fun ingresarListas(opcion: Int): Pacientes {
        if (opcion != 1 && opcion!= 2 ) {
            throw OpcionInvalida(opcion.toString())
        }

        return hospitalRepositoryListas.ingresarListas(opcion)
    }

//    override fun darAlta(): Pacientes {
//        TODO("Not yet implemented")
//    }

    fun numPacientesListas(): Int {
        return hospitalRepositoryListas.numPacientesListas()
    }

    fun todosPacientesListas(): List<Pacientes> {
        return hospitalRepositoryListas.todosPacientesListas()
    }

    fun pacientesPorDniListas(): List<Pacientes> {
        return hospitalRepositoryListas.pacientesPorDniListas()
    }

    fun pacientesOrdeFechaIngresoListas(): List<Pacientes> {
        return hospitalRepositoryListas.pacientesOrderFechaIngresoListas()
    }

    fun pacientesPorNombreIngresoListas(): List<Pacientes> {
        return hospitalRepositoryListas.pacientesPorNombreIngresoListas()
    }

    fun pacientesPorTipoListas(): List<Pacientes> {
        return hospitalRepositoryListas.pacientesPorTipoListas()
    }

    fun numPacientesPorTipoListas(): String {
        return hospitalRepositoryListas.numPacientesPorTipoListas()
    }

    fun isCompletoConjuntos(): Boolean {
        return hospitalRepositoryConjuntos.isCompletoConjuntos()
    }

    fun ingresarConjuntos(opcion: Int): Pacientes {
        if (opcion != 1 && opcion!= 2 ) {
            throw OpcionInvalida(opcion.toString())
        }

        return hospitalRepositoryConjuntos.ingresarConjuntos(opcion)
    }


    fun numPacientesConjuntos(): Int {
        return hospitalRepositoryConjuntos.numPacientesConjuntos()
    }

    fun todosPacientesConjuntos(): MutableSet<Pacientes> {
        return hospitalRepositoryConjuntos.todosPacientesConjuntos()
    }

    fun pacientesPorDniConjuntos(): MutableSet<Pacientes> {
        return hospitalRepositoryConjuntos.pacientesPorDniConjuntos()
    }

    fun pacientesOrdeFechaIngresoConjuntos(): MutableSet<Pacientes> {
        return hospitalRepositoryConjuntos.pacientesOrderFechaIngresoConjuntos()
    }

    fun pacientesPorNombreIngresoConjuntos(): MutableSet<Pacientes> {
        return hospitalRepositoryConjuntos.pacientesPorNombreIngresoConjuntos()
    }

    fun pacientesPorTipoConjuntos(): MutableSet<Pacientes> {
        return hospitalRepositoryConjuntos.pacientesPorTipoConjuntos()
    }

    fun numPacientesPorTipoConjuntos(): String {
        return hospitalRepositoryConjuntos.numPacientesPorTipoConjuntos()
    }

    fun isCompletoMapas(): Boolean {
        return hospitalRepositoryMapas.isCompletoMapas()
    }

    fun ingresarMapas(opcion: Int): Pacientes {
        if (opcion != 1 && opcion != 2 ) {
            throw OpcionInvalida(opcion.toString())
        }
        return hospitalRepositoryMapas.ingresarMapas(opcion)
    }

//    override fun darAlta(): Pacientes {
//        TODO("Not yet implemented")
//    }

    fun numPacientesMapas(): Int {
        return hospitalRepositoryMapas.numPacientesMapas()
    }

    fun todosPacientesMapas(): MutableMap<String, Pacientes> {
        return hospitalRepositoryMapas.todosPacientesMapas()
    }

    fun pacientesPorDniMapas(): MutableMap<String, Pacientes> {
        return hospitalRepositoryMapas.pacientesPorDniMapas()
    }

    fun pacientesOrdeFechaIngresoMapas(): MutableMap<String, Pacientes> {
        return hospitalRepositoryMapas.pacientesOrderFechaIngresoMapas()
    }

    fun pacientesPorNombreIngresoMapas(): MutableMap<String, Pacientes> {
        return hospitalRepositoryMapas.pacientesPorNombreIngresoMapas()
    }

    fun pacientesPorTipoMapas(): MutableMap<String, Pacientes> {
        return hospitalRepositoryMapas.pacientesPorTipoMapas()
    }

    fun numPacientesPorTipoMapas(): String {
        return hospitalRepositoryMapas.numPacientesPorTipoMapas()
    }

}