package factories

import enums.tipoPaciente
import models.Paciente

object PacientesFactory {
    val nombre = "Factoría de personas"
    var dia = 14
    var mes = 2
    var anyo = 2023

    val nombres = arrayOf(
        "Lucas",
        "Sonia",
        "Pedro",
        "Jose Miguel",
        "Rebeca",
        "Natalia",
        "Lucía",
        "Fernando",
        "Víctor",
        "Juan",
        "Hernán",
        "Nuria",
        "Pablo",
        "Rosa",
        "Pedro"
    )
    val apellidos = arrayOf(
        "Pérez",
        "Martín",
        "Dominguez",
        "González",
        "Paz",
        "Maestre",
        "Poza",
        "Gulán",
        "Díaz",
        "Solis",
        "Fuente",
        "Gómez",
        "Mirlo",
        "Quintana",
        "Pérez"
    )

    var dnis = HashMap<String, String>()

    fun agregarPaciente(): Paciente {
        val paciente = generarPaciente()
        dnis[paciente.DNI] = paciente.nombre
        return paciente
    }

    private fun generarPaciente(): Paciente {
        return Paciente(
            generarDNI(),
            nombres.random(),
            apellidos.random(),
            crearFechaRandom(),
            insertarFechaIngreso(),
            asignarTipo())
    }

    private fun generarDNI(): String {
        var dni = ""

        do {
            repeat(8) { dni += ((0..9).random()).toString() }
            dni += ('A'..'Z').random()
        } while (dnis.containsKey(dni))

        return dni
    }

    private fun crearFechaRandom(): String {
        return "\"${(1..31).random()}-${(1..12).random()}-${(1940..2020).random()}\""
    }

    private fun insertarFechaIngreso(): Triple<Int, Int, Int> {
        dia += (1..5).random()
        if (dia > 31) {
            dia -= 31
            mes++
        }
        if (mes > 12) {
            mes = 1
            anyo++
        }
        return Triple(dia, mes, anyo)
    }

    private fun asignarTipo(): tipoPaciente {
        return when((1..100).random()) {
            in 1..70 -> tipoPaciente.NORMAL
            else -> tipoPaciente.URGENCIA
        }
    }
}