package repositories

import models.TipoMotor
import models.Vehiculo

interface IItvRepository {
    fun listarvehiculos():List<Vehiculo>
    fun listarVehiculosRevisados():List<Vehiculo>
    fun listarVehiculosRevisadosDescartados():List<Vehiculo>
    fun getInfoVehiculo(matricula: String):Vehiculo?
    fun actualizarVehiculo(matricula:String,fechaNueva:String,apto:Boolean):Vehiculo?
    fun listarporTipo(tipo:String):List<Vehiculo>
    fun listarporModelo(tipo: String):List<Vehiculo>
}