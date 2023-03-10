package repositories

import enums.tipoPaciente
import factories.PacientesFactory.agregarPaciente
import models.Paciente
import `typealias`.ListaPacientes

class HospitalRepositoryList() : HospitalRepository<Paciente, String> {

    val aforoMaximo = 30
    var pacientes = arrayListOf<Paciente>(
        Paciente("58496751Y", "Laura", "Díaz", "28-11-1995", Triple(11, 2, 2023), tipoPaciente.NORMAL),
        Paciente("84951625P", "Victor", "Solis", "19-03-1986", Triple(11, 2, 2023), tipoPaciente.URGENCIA),
        Paciente("45962587A", "Sonia", "Martín", "14-03-1977", Triple(12, 2, 2023), tipoPaciente.NORMAL)
    )

    override fun ingresar(): Paciente? { // CREATE
        if (!isHospitalCompleto()) {
            val paciente = agregarPaciente()
            pacientes.add(paciente)
            return paciente
        }
        return null
    }

    override fun getAll(): ListaPacientes {
        return pacientes.toList()
    }

    override fun getByDNI(DNI: String): Paciente? {
        for (paciente in pacientes) {
            if (paciente.DNI == DNI) return paciente
        }
        return null
    }

    override fun darAlta(entity: Paciente): Paciente? { // DELETE
        if (pacientes.size > 0) {
            for (paciente in pacientes) {
                if (paciente == entity) {
                    pacientes.remove(entity)
                    return asignarFechaAlta(entity)
                }
            }
        }
        return null
    }

    private fun asignarFechaAlta(entity: Paciente): Paciente {

        var dia = entity.fechaIngreso.first + (1..3).random()
        var mes = entity.fechaIngreso.second
        var anyo = entity.fechaIngreso.third

        if (dia > 31) {
            dia -= 31
            mes++
        }
        if (mes > 12) {
            mes = 1
            anyo++
        }

        entity.fechaAlta = Triple(dia, mes, anyo)

        return entity
    }

    override fun numPacientes(): Int {
        return pacientes.size
    }

    override fun isHospitalCompleto(): Boolean {
        return pacientes.size == aforoMaximo
    }

    override fun orderByFechaIngreso(): ListaPacientes {
        return pacientes.toList()
    }

    override fun orderByNombre(): ListaPacientes {
        return pacientes.sortedBy { it.nombre }
    }

    override fun pacientesPorTipo(tipo: tipoPaciente): ListaPacientes {
        var listaTipo = ArrayList<Paciente>(0)

        for (paciente in pacientes) {
            if (paciente.tipoPaciente == tipo) listaTipo.add(paciente)
        }

        return listaTipo.toList()
    }

    override fun numPacientesTipo(tipo: tipoPaciente): Int {
        return pacientesPorTipo(tipo).size
    }
}