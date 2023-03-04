package HospitalCollections.models

import java.time.LocalDate

class Patient(
    private val dni: String?,
    private val name: String?,
    private val dateNac: String?,
    private val dateEnter: String?,
    private val typePatient: TypePatient?
) {
    val dateExit: LocalDate

    init {
        dateExit = LocalDate.now()
    }

    override fun toString(): String {
        return "Patient{" +
                "dni='" + dni + '\'' +
                ", name='" + name + '\'' +
                ", typePatient=" + typePatient +
                ", dateNac='" + dateNac + '\'' +
                ", dateEnter='" + dateEnter + '\'' +
                ", dateExit=" + dateExit +
                '}'
    }

    fun getDni(): String? {
        return dni
    }

    fun getName(): String? {
        return name
    }

    val type: TypePatient?
        get() = typePatient

    fun getDateEnter(): String? {
        return dateEnter
    }

    fun getDateNac(): String? {
        return dateNac
    }

    enum class TypePatient {
        NORMAL, URGENT
    }
}