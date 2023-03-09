package models

class Automovil(val numeroPlazas:Int,
                override var matricula: String, override var modelo:String, override var tipoMotor: TipoMotor,
                override var   kilometrosVehiculo:Int, override var fechaMatriculacion:String,
                override var fechaUltimaRevision:String, override var apto:Boolean) :Vehiculo(){
    override fun toString(): String {
        return "Matricula:$matricula Modelo:$modelo Motor:$tipoMotor Km:$kilometrosVehiculo " +
        "f.Mat:$fechaMatriculacion f.Revision:$fechaUltimaRevision apto:$apto numeroPlazas:$numeroPlazas "+"\n"
    }
}