package exceptions

sealed class RepositoryException(message: String): RuntimeException(message)
class PacienteError(message: String): RepositoryException(message)
class PacienteNoEncontrado(message: String): RepositoryException(message)
class RepositorioLleno(message: String): RepositoryException(message)


