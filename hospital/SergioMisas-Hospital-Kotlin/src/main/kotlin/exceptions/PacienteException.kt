package exceptions

abstract class HospitalException(message: String): Exception(message)

class PacienteNoEncontradoException(message: String): HospitalException(message)

class TipoPacienteException(message: String): HospitalException(message)

class HospitalLLenoException(message: String):HospitalException(message)

class HospitalVacioException(message: String):HospitalException(message)


