package exceptions

sealed class HospitalException(message: String) : Exception(message)
class IngresoException(message: String) : HospitalException(message)
class PacienteNotFoundException(message: String) : HospitalException(message)
class HospitalVacioException(message: String) : HospitalException(message)