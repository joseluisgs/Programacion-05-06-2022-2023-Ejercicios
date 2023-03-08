package models

open class Pacientes(val dni: String, val nombre: String, val fNacimiento:String, val fIngreso: String, val fAlta: String): Comparable<Pacientes> {
    open override fun toString(): String {
        return "Pacientes(dni='$dni', nombre='$nombre', fNacimiento='$fNacimiento', fIngreso='$fIngreso', fAlta='$fAlta')"
    }
    override fun compareTo(other: Pacientes): Int {
        return this.compareTo(other)
    }

}

class PacienteComparatorDNI : Comparator<Pacientes> {
    override fun compare(o1: Pacientes, o2: Pacientes): Int {
        return o1.dni.compareTo(o2.dni)
    }
}

class PacienteComparatorFIngreso : Comparator<Pacientes> {
    override fun compare(o1: Pacientes, o2: Pacientes): Int {
        return o1.fIngreso.compareTo(o2.fIngreso)
    }
}

class PacienteComparatorNombre : Comparator<Pacientes> {
    override fun compare(o1: Pacientes, o2: Pacientes): Int {
        return o1.nombre.compareTo(o2.nombre)
    }
}

class PacienteComparatorTipo : Comparator<Pacientes> {
    override fun compare(o1: Pacientes, o2: Pacientes): Int {
        return when{
            o1 is PNormal && o2 is PUrgencia -> -1
            o1 is PUrgencia && o2 is PNormal -> 1
            else -> 0
        }
    }
}