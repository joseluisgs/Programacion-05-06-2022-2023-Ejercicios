package repositories

import enums.tipoPaciente
import factories.PacientesFactory.agregarPaciente
import models.Paciente
import `typealias`.ListaPacientes

class HospitalRepositoryMap() : HospitalRepository<Paciente, String> {

    val aforoMaximo = 30
    var pacientes = hashMapOf<Int, Paciente>(
        Pair(1, Paciente("58496751Y", "Laura", "Díaz", "28-11-1995", Triple(11, 2, 2023), tipoPaciente.NORMAL)),
        Pair(2, Paciente("84951625P", "Victor", "Solis", "19-03-1986", Triple(11, 2, 2023), tipoPaciente.URGENCIA)),
        Pair(3, Paciente("45962587A", "Sonia", "Martín", "14-03-1977", Triple(12, 2, 2023), tipoPaciente.NORMAL))
    )

    override fun ingresar(): Paciente? { // CREATE
        if (!isHospitalCompleto()) {
            pacientes[pacientes.keys.last() + 1] = agregarPaciente()
            return pacientes.values.last()
        }
        return null
    }

    override fun getAll(): ListaPacientes {
        return pacientes.values.toList()
    }

    override fun getByDNI(DNI: String): Paciente? {
        for (paciente in pacientes.values) {
            if (paciente.DNI == DNI) return paciente
        }
        return null
    }

    override fun darAlta(entity: Paciente): Paciente? { // DELETE
        if (pacientes.size > 0) {
            for (paciente in pacientes) {
                if (paciente.value == entity) {
                    pacientes.remove(paciente.key)
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
        return pacientes.values.toList()
    }

    override fun orderByNombre(): ListaPacientes {
        return pacientes.values.sortedBy { it.nombre }
    }

    override fun pacientesPorTipo(tipo: tipoPaciente): ListaPacientes {
        return pacientes.values.filter { it.tipoPaciente == tipo }
    }

    override fun numPacientesTipo(tipo: tipoPaciente): Int {
        return pacientesPorTipo(tipo).size
    }
}