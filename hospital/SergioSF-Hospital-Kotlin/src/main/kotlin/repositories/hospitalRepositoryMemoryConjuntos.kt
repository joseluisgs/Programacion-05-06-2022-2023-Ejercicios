package repositories

import models.*
import java.util.*

private var dni: String = ""
private var nombre: String = ""
private var fNacimiento: String = ""
private var fIngreso: String = ""
private var fAlta: String = ""

class hospitalRepositoryMemoryConjuntos: hospitalRepositoryConjuntos {

    private val pacienteNormal0 = PNormal("98397416N", "Pedro Picapiedra", "1979-01-31", "1981-03-21", "2000-20-03")
    private val pacienteNormal1 = PNormal("27453044Z", "Celia Gallego", "1956-05-24", "1971-03-29", "1976-27-05")
    private val pacienteUrgencia0 = PUrgencia("55755084W", "Miguel Ibañez", "1973-05-25", "2000-01-01", "2001-09-28")
    private val pacienteUrgencia1 = PUrgencia("79192293G", "Sandra Moya", "1980-09-08", "2007-12-10", "2008-12-15")

    private val conjuntoPacientes = mutableSetOf( pacienteUrgencia1 ,pacienteNormal0, pacienteNormal1, pacienteUrgencia0, pacienteUrgencia1)

    override fun isCompletoConjuntos(): Boolean {
        return conjuntoPacientes.size == 50
    }

    override fun ingresarConjuntos(opcion: Int): Pacientes {
        introducirDatos()
        if(opcion == 1){
            conjuntoPacientes.add(PNormal(dni, nombre, fNacimiento, fIngreso, fAlta))
        } else if(opcion == 2){
            conjuntoPacientes.add(PUrgencia(dni, nombre, fNacimiento, fIngreso, fAlta))
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

    override fun numPacientesConjuntos(): Int {
        return conjuntoPacientes.size
    }

    override fun todosPacientesConjuntos(): MutableSet<Pacientes> {
        return conjuntoPacientes
    }

    override fun pacientesPorDniConjuntos(): MutableSet<Pacientes> {
//        var conjuntoPacientesCopy = TreeSet<Pacientes>() { p1, p2 -> p1.dni.compareTo(p2.dni) }
        val conjuntoPacientesCopy = TreeSet<Pacientes> ( PacienteComparatorDNI() )
        conjuntoPacientesCopy.addAll(conjuntoPacientes)
        return conjuntoPacientesCopy
    }

    override fun pacientesOrderFechaIngresoConjuntos(): MutableSet<Pacientes> {
        val conjuntoPacientesCopy = TreeSet<Pacientes> ( PacienteComparatorFIngreso() )
        conjuntoPacientesCopy.addAll(conjuntoPacientes)
        return conjuntoPacientesCopy
    }

    override fun pacientesPorNombreIngresoConjuntos(): MutableSet<Pacientes> {
        val conjuntoPacientesCopy = TreeSet<Pacientes> ( PacienteComparatorNombre())
        conjuntoPacientesCopy.addAll(conjuntoPacientes)
        return conjuntoPacientesCopy
    }

    override fun pacientesPorTipoConjuntos(): MutableSet<Pacientes> {
        val conjuntoPacientesCopy = conjuntoPacientes.toMutableList()
        conjuntoPacientesCopy.sortWith(PacienteComparatorTipo())
        return conjuntoPacientesCopy.toMutableSet()

//        val conjuntoPacientesCopy = TreeSet<Pacientes> (PacienteComparatorTipo())
//        conjuntoPacientesCopy.addAll(conjuntoPacientes)
//        return conjuntoPacientesCopy
    }

    override fun numPacientesPorTipoConjuntos(): String {
        var numNormal = 0
        var numUrgencia = 0
        var otros = 0
        conjuntoPacientes.forEach{
            when (it) {
                is PNormal -> numNormal++
                is PUrgencia -> numUrgencia++
                else -> otros++
            }
        }
        return "Paciente tipo Normal: $numNormal, Urgencia: $numUrgencia, Otro: $otros"
    }
}