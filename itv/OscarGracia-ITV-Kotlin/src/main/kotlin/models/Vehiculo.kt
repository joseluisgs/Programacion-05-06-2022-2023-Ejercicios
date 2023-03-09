package models

abstract class Vehiculo():IVehiculo{
        override val matricula: String= ""
        override var modelo: String =""
        override var tipoMotor: TipoMotor= TipoMotor.Hibrido
        override var kilometrosVehiculo: Int = 0
        override var fechaMatriculacion: String = ""
        override var fechaUltimaRevision: String = ""
        override var apto: Boolean = false
        override fun toString(): String {
                return "Matricula:$matricula Modelo:$modelo Motor:$tipoMotor Km:$kilometrosVehiculo " +
                        "f.Mat:$fechaMatriculacion f.Revision:$fechaUltimaRevision apto:$apto " +"\n"
        }

}