package models

interface IVehiculo {
    val matricula:String
    val modelo:String
    val tipoMotor:TipoMotor
    var kilometrosVehiculo:Int
    val fechaMatriculacion:String
    var fechaUltimaRevision:String
    var apto:Boolean
}

enum class TipoMotor{
    Gasolina,Gasoleo,Hibrido,Electrico
}