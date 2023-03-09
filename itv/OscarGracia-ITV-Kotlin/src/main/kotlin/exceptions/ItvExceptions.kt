package exceptions

    sealed class ItvExceptions(message: String) : RuntimeException(message)
    class ItvSinCochesException() : ItvExceptions("No existe Vehiculo ")
    class ItvEntradaMal(motivo: String) : ItvExceptions("No se ha podido crear el medico: $motivo")
    class ITVMatriculamal():ItvExceptions("La matricula no cumple el Regex")
