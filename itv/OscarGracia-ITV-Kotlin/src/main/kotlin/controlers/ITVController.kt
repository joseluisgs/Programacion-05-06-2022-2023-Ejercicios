package controlers

import exceptions.ItvEntradaMal
import exceptions.ItvSinCochesException
import models.*
import repositories.ItvRepository

class ITVController(private var dataBase:ItvRepository) {

    fun listarvehiculos():List<Vehiculo>{
        var salida = dataBase.listarvehiculos()
        if(salida.isEmpty()) throw ItvSinCochesException()
        return salida
    }
    fun listarVehiculosRevisados():List<Vehiculo>{
        var salida = dataBase.listarVehiculosRevisados()
        if(salida.isEmpty()) throw ItvSinCochesException()
        return salida
    }

    fun listarVehiculosRevisadosDescartados():List<Vehiculo>{
        var salida = dataBase.listarVehiculosRevisadosDescartados()
        if(salida.isEmpty()) throw ItvSinCochesException()
        return salida
    }
    fun getInfoVehiculo(matricula: String): Vehiculo?{
        var salida = dataBase.getInfoVehiculo(matricula)
        if(salida ==null) throw ItvSinCochesException()
        return salida
    }
    fun actualizarVehiculo(matricula:String,fechaNueva:String,apto:Boolean): Vehiculo? {
        var salida = dataBase.actualizarVehiculo(matricula,fechaNueva,apto)
        if(salida ==null) throw ItvSinCochesException()
        return  salida
    }
    fun listarporTipo(tipo: String):List<Vehiculo?> {
        var salida = dataBase.listarporTipo(tipo.trim())
        if(salida[0]== null)throw ItvSinCochesException()
        return salida
    }
    fun listarporModelo(tipo: String):List<Vehiculo>{
        var salida = dataBase.listarporModelo(tipo.trim())
        if(salida[0]== null)throw ItvSinCochesException()
        return salida

    }
}

