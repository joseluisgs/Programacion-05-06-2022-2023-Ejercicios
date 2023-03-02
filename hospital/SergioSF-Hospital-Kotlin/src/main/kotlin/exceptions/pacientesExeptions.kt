package exceptions

sealed class pacientesExeptions
class OpcionInvalida(message: String) : Exception("La opcion, $message, no es válida escoge un número válido")
class OptionNull(message: String) : Exception("La opcion, $message, no es un null introduce algún número válido")
