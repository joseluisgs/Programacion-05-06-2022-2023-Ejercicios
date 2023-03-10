package exceptions

sealed class VehiculoException(message: String): RuntimeException(message)
class NoExistenVehiculosException(): VehiculoException("No existen vehiculos en la base de datos")