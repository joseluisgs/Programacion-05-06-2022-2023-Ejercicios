package repositories

import models.PNormal
import models.PUrgencia
import models.Pacientes

private var dni: String = ""
private var nombre: String = ""
private var fNacimiento: String = ""
private var fIngreso: String = ""
private var fAlta: String = ""

class hospitalRepositoryMemoryListas: hospitalRepositoryListas {


    private val pacienteNormal0 = PNormal("98397416N", "Pedro Picapiedra", "1979-01-31", "1981-03-21", "2000-20-03")
    private val pacienteNormal1 = PNormal("27453044Z", "Celia Gallego", "1956-05-24", "1971-03-29", "1976-27-05")
    private val pacienteUrgencia0 = PUrgencia("55755084W", "Miguel Ibañez", "1973-05-25", "2000-01-01", "2001-09-28")
    private val pacienteUrgencia1 = PUrgencia("79192293G", "Sandra Moya", "1980-09-08", "2007-12-10", "2008-12-15")


    private val listaPacientes = mutableListOf( pacienteUrgencia1 ,pacienteNormal0, pacienteNormal1, pacienteUrgencia0, pacienteUrgencia1)


    override fun isCompletoListas(): Boolean {
        return listaPacientes.size == 50
    }
    override fun ingresarListas(opcion: Int): Pacientes {
        introducirDatos()
        if(opcion == 1){
            listaPacientes.add(PNormal(dni, nombre, fNacimiento, fIngreso, fAlta))
        } else if(opcion == 2){
            listaPacientes.add(PUrgencia(dni, nombre, fNacimiento, fIngreso, fAlta))
        }
        return Pacientes(dni, nombre, fNacimiento, fIngreso, fAlta)
    }

    private fun introducirDatos() {
        println("Introduce el Dni")
        dni = introducirDni()
        println("Introduce el nombre")
        nombre = introducirNombre()
        println("Introduce la fecha de nacimiento")
        fNacimiento = introducirFecha()
        println("Introduce la fecha de ingreso")
        fIngreso = introducirFecha()
        println("Introduce la fecha de alta")
        fAlta = introducirFecha()
    }

    private fun introducirFecha(): String {
        var fecha: String
        do{
            fecha  = readln()
            val isValido: Boolean = validaFecha(fecha)
        }while (!isValido)
        return fecha
    }

    private fun validaFecha(fecha: String): Boolean {
        val regex: Regex = Regex("(19|20)\\d\\d-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])")
        if(regex.containsMatchIn(fecha)){
            return true
        }
        return false
    }

    private fun introducirNombre(): String {
        var nombre: String
        do{
            nombre  = readln()
            val isValido: Boolean = validaNombre(nombre)
        }while (!isValido)
        return nombre
    }

    private fun validaNombre(nombre: String): Boolean {
        val regex: Regex = Regex("^([a-zA-Z]{2,}\\s[a-zA-Z]{1,}'?-?[a-zA-Z]{2,}\\s?([a-zA-Z]{1,})?)")
        if(regex.containsMatchIn(nombre)){
            return true
        }
        return false
    }

    private fun introducirDni(): String {
        var dni: String
        do{
            println("Introduce un el dni")
            dni  = readln()
            val isValido: Boolean = validaDNI(dni)
        }while (!isValido)
        return dni
    }

    private fun validaDNI(dni: String): Boolean {
        val regex: Regex = Regex("^[0-9]{8,8}[A-Za-z]\$")
        if(regex.containsMatchIn(dni)){
            return true
        }
        return false
    }

//    override fun darAlta(): Pacientes {
//        TODO("Not yet implemented")
//    }

    override fun numPacientesListas(): Int {
       return listaPacientes.size
    }

    override fun todosPacientesListas(): List<Pacientes> {
        return listaPacientes
    }

    override fun pacientesPorDniListas(): List<Pacientes> {
        return listaPacientes.sortedBy { it.dni }
    }

    override fun pacientesOrderFechaIngresoListas(): List<Pacientes> {
        return listaPacientes.sortedBy { it.fIngreso }
    }

    override fun pacientesPorNombreIngresoListas(): List<Pacientes> {
        return listaPacientes.sortedBy { it.nombre }
    }

    override fun pacientesPorTipoListas(): List<Pacientes> {
        val listaPacientesCopia = listaPacientes.sortedWith(compareBy (
            { it is PUrgencia },
            { it is PNormal },
//            { true }
        ))

        val listaPacientesCopia2 = listaPacientesCopia.sortedBy { it is PNormal}
        val listaPacientesCopia3 = listaPacientesCopia.filterIsInstance<PNormal>()

        println()
        println("Otra forma")
        println(listaPacientesCopia.joinToString ( "\n" ))
        println()
        println("Forma 2")
        println(listaPacientesCopia2.joinToString ( "\n" ))
        println()
        println("Forma 3 - Correcta")
        println(listaPacientesCopia3.joinToString ( "\n" ))
        println()

        return listaPacientes.sortedBy {
            when (it) {
                is PNormal -> 1
                is PUrgencia -> 2
                else -> 3
            }
        }
    }

    override fun numPacientesPorTipoListas(): String {
        var numNormal = 0
        var numUrgencia = 0
        var otros = 0
         listaPacientes.forEach {
            when (it) {
                is PNormal -> numNormal++
                is PUrgencia -> numUrgencia++
                else -> otros++
            }
        }
        return "Paciente tipo Normal: $numNormal, Urgencia: $numUrgencia, Otro: $otros"
    }
}