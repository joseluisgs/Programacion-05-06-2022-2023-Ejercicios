package controlers

import exceptions.PacienteError
import exceptions.PacienteNoEncontrado
import models.Paciente
import models.TipoPaciente
import repositories.IHospitalRepository

class MedicosControler(var repositorio:IHospitalRepository) {
    val logger = mu.KotlinLogging.logger {}
    init {
        logger.info { " INICIANDO Contolador" }
    }
    fun ingresar(paciente: Paciente) {
        logger.info { "Contolador fun ingresar" }
        if (!paciente.dni.uppercase().matches("[0-9]{8}[A-Z]".toRegex())){
            throw PacienteError("El DNI ${paciente.dni} no es válido")
        }
        if(paciente.nombre.isEmpty()) throw PacienteError("El nombre esta en blanco")
        repositorio.ingresar(paciente)
    }

     fun alta(paciente: Paciente, fechaAlta:String): Paciente? {
        logger.info { "Contolador fun alta" }
        var salida =  repositorio.alta(paciente,fechaAlta)
         if(salida== null) throw PacienteNoEncontrado("La persona con DNI ${paciente.dni} no existe")
         return salida
    }

     fun estaCompleto(): Boolean {
        logger.info { "Contolador fun estaCompleto" }

        return repositorio.estaCompleto()
    }

     fun numeroPacientes(): Int{
        logger.info { "Contolador fun numeroPacientes" }
        return repositorio.numeroPacientes()
     }

     fun obtenertodosPacientes(): Any {
        logger.info { "Contolador fun obtenertodosPacientes" }
        return repositorio.obtenertodosPacientes()
    }

     fun pacientePorDni(dni:String): Paciente? {
        logger.info { "Contolador fun pacientePorDni" }
         if (!dni.uppercase().matches("[0-9]{8}[A-Z]".toRegex())){
             throw PacienteError("El DNI $dni no es válido")
         }
        var salida: Paciente? = repositorio.pacientePorDni(dni)
       if(salida==null) throw PacienteNoEncontrado("La persona con DNI $dni no existe")
        return salida
    }

     fun pacientesOrdenadosFechaIngreso(): Any {
        logger.info { "Contolador fun pacientesOrdenadosFechaIngreso" }
        return repositorio.pacientesOrdenadosFechaIngreso()
    }

     fun pacientesOrdenadosPorNombre(): Any {
         logger.info { "Contolador fun pacientesOrdenadosPorNombre" }
        return repositorio.pacientesOrdenadosPorNombre()
    }

     fun pacientesPorTipo(tipoPaciente: TipoPaciente): Any {
         logger.info { "Contolador fun pacientesPorTipo" }
        return repositorio.pacientesPorTipo(tipoPaciente)
    }

     fun numPacientesPorTipo(tipoPaciente: TipoPaciente): Int {
         logger.info { "Contolador fun numPacientesPorTipo" }
        return  repositorio.numPacientesPorTipo(tipoPaciente)
    }

    fun borrarpaciente(paciente: Paciente){
        logger.info { "Contolador fun borrarpaciente" }
        repositorio.borrarpaciente(paciente)
    }
}