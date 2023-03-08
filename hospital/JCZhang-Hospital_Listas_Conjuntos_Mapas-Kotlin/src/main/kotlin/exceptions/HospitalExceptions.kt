package exceptions

sealed class PacientesException(message: String): RuntimeException(message)
class DNINotFoundException(message: String) : PacientesException(message)
class TypeNotFoundException(message: String): PacientesException(message)
class NotValidDNIException(message: String) : PacientesException(message)
class PacienteBadRequestException(message: String) : PacientesException(message)
