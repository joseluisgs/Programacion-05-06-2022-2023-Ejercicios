package repositories

import exceptions.RepositorioLleno
import models.Paciente
import models.TipoPaciente

class RepositoryMap:IHospitalRepository {
    val logger = mu.KotlinLogging.logger {}
    private var  db = mutableMapOf(
        "53454692F" to Paciente(
            dni = "53454692F",
            nombre = "Oscar Gracia ",
            fechaNacimiento = "1986-06-22",
            fechaIngreso = "2022-02-15",
            tipoPaciente = TipoPaciente.A
        ),
        "43443292D" to Paciente(
            dni = "43443292D",
            nombre = "Jeromin Perez",
            fechaNacimiento = "1956-03-12",
            fechaIngreso = "2023-02-15",
            tipoPaciente = TipoPaciente.B
        ),
        "523487486R" to Paciente(
            dni = "523487486R",
            nombre = "JoseFina",
            fechaNacimiento = "1986-01-18",
            fechaIngreso = "2023-02-14",
            tipoPaciente = TipoPaciente.A
        )
    )

    init {
        logger.debug { "Iniciando repositorio Hospital (List) en memoria" }

    }

    override fun ingresar(paciente: Paciente) {
        logger.debug { "Repositorio fun ingresar" }
        var estaCompleto:Boolean = estaCompleto()
        if(estaCompleto)  throw RepositorioLleno("Repositorio lleno")
        db.put(paciente.dni,paciente)
    }

    override fun alta(paciente: Paciente, fechaAlta:String): Paciente? {
        logger.debug { "Repositorio fun alta" }
        var salida = paciente
        salida.fechaAlta = fechaAlta
        db[paciente.dni] = salida
        return paciente
    }

    override fun estaCompleto(): Boolean {
        logger.debug { "Repositorio fun estaCompleto" }
        if (db.size>=50) return true
        return false
    }

    override fun numeroPacientes(): Int{
        logger.debug { "Repositorio fun numeroPacientes" }
        return db.size}

    override fun obtenertodosPacientes(): Any {
        logger.debug { "Repositorio fun obtenertodosPacientes" }
        return db
    }

    override fun pacientePorDni(dni:String): Paciente? {
        logger.debug { "Repositorio fun pacientePorDni" }
       return db[dni]
    }

    override fun pacientesOrdenadosFechaIngreso(): Any {
        logger.debug { "Repositorio fun pacientesOrdenadosFechaIngreso" }
        return db.values.sortedWith(){P1,P2->P1.fechaIngreso.compareTo(P2.fechaIngreso)}
    }

    override fun pacientesOrdenadosPorNombre(): Any {
        logger.debug { "Repositorio fun pacientesOrdenadosPorNombre" }
        return db.values.sortedWith(){P1,P2->P1.nombre.compareTo(P2.nombre)}
    }

    override fun pacientesPorTipo(tipoPaciente: TipoPaciente): Any {
        logger.debug { "Repositorio fun pacientesPorTipo" }
        return db.filter { it.value.tipoPaciente== tipoPaciente }
    }

    override fun numPacientesPorTipo(tipoPaciente: TipoPaciente): Int {
        logger.debug { "Repositorio fun numPacientesPorTipo" }
        return  db.filter { it.value.tipoPaciente== tipoPaciente }.size
    }
    override fun borrarpaciente (paciente: Paciente){
        logger.debug { "Repositorio fun borrarpaciente" }
        db.remove(paciente.dni)
    }
}