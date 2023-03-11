package exceptions

sealed class CalculadoraException(message: String) : Exception(message)
class FormatoException(message: String) : CalculadoraException(message)
class OperacionException(message: String) : CalculadoraException(message)
class OperandoException(message: String) : CalculadoraException(message)
class DividirPorCeroException(message: String) : CalculadoraException(message)