package exceptions

sealed class ItvExceptions(message: String): RuntimeException(message)
class ItvMatriculaException: ItvExceptions("Error -> Matricula no valida")
class ItvKilometroException: ItvExceptions("Error -> Kilometro no valido")
class ItvAnioException: ItvExceptions("Error -> Anio de matriculacion no valido")

class ItvPlazasException: ItvExceptions("Error -> Numero de plazas no valido")
class ItvCilindradaException: ItvExceptions("Error -> Cilindrada no valida")