package repositories

import models.*
import java.util.*

private var dni: String = ""
private var nombre: String = ""
private var fNacimiento: String = ""
private var fIngreso: String = ""
private var fAlta: String = ""

class hospitalRepositoryMemoryMapas: hospitalRepositoryMapas {

    private val pacienteNormal0 = PNormal("98397416N", "Pedro Picapiedra", "1979-01-31", "1981-03-21", "2000-20-03")
    private val pacienteNormal1 = PNormal("27453044Z", "Celia Gallego", "1956-05-24", "1971-03-29", "1976-27-05")
    private val pacienteUrgencia0 = PUrgencia("55755084W", "Miguel Ibañez", "1973-05-25", "2000-01-01", "2001-09-28")
    private val pacienteUrgencia1 = PUrgencia("79192293G", "Sandra Moya", "1980-09-08", "2007-12-10", "2008-12-15")

    private var mapaPacientes = mutableMapOf(pacienteNormal0.dni to pacienteNormal0, pacienteNormal1.dni to pacienteNormal1, pacienteUrgencia0.dni to pacienteUrgencia0, pacienteUrgencia1.dni to pacienteUrgencia1)


    override fun isCompletoMapas(): Boolean {
        return mapaPacientes.size == 50
    }

    override fun ingresarMapas(opcion: Int): Pacientes {
        introducirDatos()
        if(opcion == 1){
            mapaPacientes [dni] = PNormal(dni, nombre, fNacimiento, fIngreso, fAlta)
        } else if(opcion == 2){
            mapaPacientes [dni] = PUrgencia(dni, nombre, fNacimiento, fIngreso, fAlta)
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
    override fun numPacientesMapas(): Int {
        return mapaPacientes.size
    }

    override fun todosPacientesMapas(): MutableMap<String, Pacientes> {
        return mapaPacientes
    }

    override fun pacientesPorDniMapas(): MutableMap<String, Pacientes> {
        val mapaPacientesCopy = TreeMap<String, Pacientes> ( compareBy { mapaPacientes[it]!!.dni } )
        mapaPacientesCopy.putAll(mapaPacientes)
        return mapaPacientesCopy
    }

    override fun pacientesOrderFechaIngresoMapas(): TreeMap<String, Pacientes> {
        val mapaPacientesCopy = TreeMap<String, Pacientes> ( compareBy { mapaPacientes[it]!!.fIngreso } )
        mapaPacientesCopy.putAll(mapaPacientes)
        return mapaPacientesCopy
    }

    override fun pacientesPorNombreIngresoMapas(): MutableMap<String, Pacientes> {
        val mapaPacientesCopy = TreeMap<String, Pacientes> ( compareBy { mapaPacientes[it]!!.nombre } )
        mapaPacientesCopy.putAll(mapaPacientes)
        return mapaPacientesCopy
    }

    override fun pacientesPorTipoMapas(): MutableMap<String, Pacientes> {

        val mapaPNormal = LinkedHashMap<String, PNormal>()
        val mapaPUrgencia = LinkedHashMap<String, PUrgencia>()

        mapaPacientes.forEach { (clave, pacientes) ->
            when(pacientes) {
                is PNormal -> mapaPNormal[clave] = pacientes
                is PUrgencia -> mapaPUrgencia[clave] = pacientes
            }
        }

        val mapaPacientesCopy = TreeMap<String, Pacientes>()
        mapaPacientesCopy.putAll(mapaPNormal)
        println("Pacientes tipo Normal")
        println(mapaPacientesCopy.toList().joinToString ( "\n" ))
        mapaPacientesCopy.clear()
        mapaPacientesCopy.putAll(mapaPUrgencia)
        println("Pacientes tipo Urgencia")
        println(mapaPacientesCopy.toList().joinToString("\n"))
        return mapaPacientesCopy
    }

    override fun numPacientesPorTipoMapas(): String {
        val numNormal = mapaPacientes.values.filterIsInstance<PNormal>().count()
        val numUrgencia = mapaPacientes.values.filterIsInstance<PUrgencia>().count()

        return "Paciente tipo Normal: $numNormal, Urgencia: $numUrgencia"
    }
}