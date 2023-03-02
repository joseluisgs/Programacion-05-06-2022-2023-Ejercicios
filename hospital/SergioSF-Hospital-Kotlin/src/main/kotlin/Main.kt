import controler.HospitalController
import repositories.*

val hospitalController = HospitalController(
    hospitalRepositoryMemoryListas(),
    hospitalRepositoryMemoryConjuntos(),
    hospitalRepositoryMemoryMapas()
)

fun main() {
    println("Listas")
    listas()
    println()

    println("Conjuntos")
    conjuntos()
    println()

    println("Mapas")
    mapas()
}

fun listas() {

    println("IsCompleto")
    val isCompleto = hospitalController.isCompletoListas()
    println(isCompleto)

    println("Ingresar Pacientes")
    // PNormal = 1 PUrgencia = 2
    val opcion = escogerTipo()
    val ingresarPacientes = hospitalController.ingresarListas(opcion)
    println(ingresarPacientes)

    println("Numero de pacientes")
    val numeroPacientes = hospitalController.numPacientesListas()
    println(numeroPacientes)

    println("Todos los Pacientes")
    val todosPacientes = hospitalController.todosPacientesListas()
    println(todosPacientes.joinToString("\n"))

    println("Pacientes por DNI")
    val pacientesByDNI = hospitalController.pacientesPorDniListas()
    println(pacientesByDNI.joinToString("\n"))

    println("Pacientes por fecha de ingreso")
    val pacientesOrderFechaIngreso = hospitalController.pacientesOrdeFechaIngresoListas()
    println(pacientesOrderFechaIngreso.joinToString("\n"))

    println("Pacientes por nombre")
    val pacientesNombre = hospitalController.pacientesPorNombreIngresoListas()
    println(pacientesNombre.joinToString("\n"))

    println("Pacientes por tipo")
    val pacientesPorTipo = hospitalController.pacientesPorTipoListas()
    println(pacientesPorTipo.joinToString("\n"))

    println("Numero de pacientes")
    val numPacientesPorTipo = hospitalController.numPacientesPorTipoListas()
    println(numPacientesPorTipo)
}

fun conjuntos() {
    println("IsCompleto")
    val isCompleto = hospitalController.isCompletoConjuntos()
    println(isCompleto)

    println("Ingresar Pacientes")
    // PNormal = 1 PUrgencia = 2
    val opcion = escogerTipo()
    val ingresarPacientes = hospitalController.ingresarConjuntos(opcion)
    println(ingresarPacientes)

    println("Numero de pacientes")
    val numeroPacientes = hospitalController.numPacientesConjuntos()
    println(numeroPacientes)

    println("Todos los Pacientes")
    val todosPacientes = hospitalController.todosPacientesConjuntos()
    println(todosPacientes.joinToString("\n"))

    println("Pacientes por DNI")
    val pacientesByDNI = hospitalController.pacientesPorDniConjuntos()
    println(pacientesByDNI.joinToString("\n"))

    println("Pacientes por fecha de ingreso")
    val pacientesOrderFechaIngreso = hospitalController.pacientesOrdeFechaIngresoConjuntos()
    println(pacientesOrderFechaIngreso.joinToString("\n"))

    println("Pacientes por nombre")
    val pacientesNombre = hospitalController.pacientesPorNombreIngresoConjuntos()
    println(pacientesNombre.joinToString("\n"))

    println("Pacientes por tipo")
    val pacientesPorTipo = hospitalController.pacientesPorTipoConjuntos()
    println(pacientesPorTipo.joinToString("\n"))

    println("Numero de pacientes")
    val numPacientesPorTipo = hospitalController.numPacientesPorTipoConjuntos()
    println(numPacientesPorTipo)
}

fun mapas() {
//    println("IsCompleto")
    val isCompleto = hospitalController.isCompletoMapas()
    println(isCompleto)

    println("Ingresar Pacientes")
    // PNormal = 1 PUrgencia = 2
    val opcion = escogerTipo()
    val ingresarPacientes = hospitalController.ingresarMapas(opcion)
    println(ingresarPacientes)

    println("Numero de pacientes")
    val numeroPacientes = hospitalController.numPacientesMapas()
    println(numeroPacientes)

    println("Todos los Pacientes")
    val todosPacientes = hospitalController.todosPacientesMapas()
    println(todosPacientes.toList().joinToString("\n"))

    println("Pacientes por DNI")
    val pacientesByDNI = hospitalController.pacientesPorDniMapas()
    println(pacientesByDNI.toList().joinToString("\n"))

    println("Pacientes por fecha de ingreso")
    val pacientesOrderFechaIngreso = hospitalController.pacientesOrdeFechaIngresoMapas()
    println(pacientesOrderFechaIngreso.toList().joinToString("\n"))

    println("Pacientes por nombre")
    val pacientesNombre = hospitalController.pacientesPorNombreIngresoMapas()
    println(pacientesNombre.toList().joinToString("\n"))

    println("Pacientes por tipo")
    val pacientesPorTipo = hospitalController.pacientesPorTipoMapas()
//    println(pacientesPorTipo.toList().joinToString("\n"))

    println("Numero de pacientes")
    val numPacientesPorTipo = hospitalController.numPacientesPorTipoMapas()
    println(numPacientesPorTipo)
}

fun escogerTipo(): Int {
    var isElegido = false
    var opcion = ""
    do {
        println("Escoge el tipo de paciente que quieres crear: PNormal = 1 PUrgencia = 2")
        opcion = readln()
        isElegido = validaOpcion(opcion)
    }while (!isElegido)
    return opcion.toInt()
}

fun validaOpcion(opcion: String): Boolean {
    val regex: Regex = Regex("[1-2]")
    return regex.containsMatchIn(opcion)
}
