import controlers.ControllerHospital
import models.Paciente
import models.TipoPaciente
import repositories.RepositoryMap

fun main() {
    println("Hola Hospital!")

    val medicosController = ControllerHospital(RepositoryMap())
    //probamos obtener todos
    var res = medicosController.obtenertodosPacientes()
    println(res.toString())

    var pacienteParaIngreso:Paciente = Paciente(dni = "12345678F", nombre = "Anabel", fechaNacimiento = "1990-09-24",
        fechaIngreso = "2022-03-07", tipoPaciente = TipoPaciente.D)
    //probamos añadirlo y obtener
     medicosController.ingresar(pacienteParaIngreso)
     var res2= medicosController.pacientePorDni(pacienteParaIngreso.dni)
    println(res2.toString())
    //funciones de isLleno y size
    println(medicosController.estaCompleto())
    println(medicosController.numeroPacientes())
    // obtener por tipo y numero de ellos
    println(medicosController.pacientesPorTipo(tipoPaciente = TipoPaciente.A))
    println(medicosController.numPacientesPorTipo(tipoPaciente = TipoPaciente.A))
    // ordenacion
     res = medicosController.pacientesOrdenadosFechaIngreso()
    println(res.toString())
    res = medicosController.pacientesOrdenadosPorNombre()
    println(res.toString())
    //alta y borrar
    medicosController.alta(pacienteParaIngreso,"2023-03-09")
    println(medicosController.pacientePorDni(pacienteParaIngreso.dni)!!.fechaAlta)
    medicosController.borrarpaciente(pacienteParaIngreso)
    res = medicosController.obtenertodosPacientes()
    println(res.toString())
}