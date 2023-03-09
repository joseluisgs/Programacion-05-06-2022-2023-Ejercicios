package exceptions

sealed class HospitalExceptions(message: String): RuntimeException(message)
class HospitalFullException: HospitalExceptions("Error -> Hospital lleno.")
// class PacienteNotFoundException: HospitalExceptions("Error -> Paciente no encontrado.")
class DniNotValidException: HospitalExceptions("Error -> DNI no válido.")