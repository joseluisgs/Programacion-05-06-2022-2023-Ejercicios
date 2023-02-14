package exceptions

sealed class PacienteException(message: String): RuntimeException(message)
class PacientesAreEmptyException(message: String): PacienteException(message)
class PacienteBadRquestException(message: String): PacienteException(message)
class PacienteNotFoundException(message: String): PacienteException(message)
class PacientesAreFullException(message: String): PacienteException(message)
