package repositories

import models.*

class ItvRepository():IItvRepository {
   private var dataBase =  mutableListOf<Vehiculo>(
         Camion(1500,"1234-RTW","Sk38",TipoMotor.Electrico, kilometrosVehiculo = 612320,
            "2010-01-01","2022-01-17",apto = false),
        Motocicleta(1000,"8434-BRW","C43",TipoMotor.Hibrido, kilometrosVehiculo = 124,
            "2019-03-21","2023-03-11",apto = true),
       Motocicleta(1000,"8434-BRW","C43",TipoMotor.Gasoleo, kilometrosVehiculo = 12321,
           "2015-06-11","2022-08-11",apto = true),
        Automovil(2,"6654-STW","Corola",TipoMotor.Gasolina, kilometrosVehiculo = 20,
            "2000-11-07","2022-02-17",apto = true),
       Automovil(3,"9834-FDW","Corola",TipoMotor.Gasolina, kilometrosVehiculo = 3230,
           "2020-12-07","2022-02-17",apto = false)
   )

    override fun listarvehiculos(): List<Vehiculo> {
        return dataBase
    }

    override fun listarVehiculosRevisados(): List<Vehiculo> {

        return dataBase.filter{ it.apto }
    }

    override fun listarVehiculosRevisadosDescartados(): List<Vehiculo> {

        return dataBase.filter{ !it.apto }
    }

    override fun getInfoVehiculo(matricula: String): Vehiculo? {
        var salida = dataBase.filter { it.matricula==matricula}
        if(salida.isEmpty())return null
        return salida[0]
    }

    override fun actualizarVehiculo(matricula: String,fechaNueva:String,apto:Boolean): Vehiculo? {
        var dataBaseFiltrada= dataBase.filter { it.matricula==matricula}
        if(dataBaseFiltrada.isEmpty()) return null
        dataBase.remove(dataBaseFiltrada[0])
         dataBaseFiltrada[0].apto = true
        dataBaseFiltrada[0].fechaUltimaRevision= fechaNueva
        dataBase.add(dataBaseFiltrada[0])
        return dataBaseFiltrada[0]
    }

    override fun listarporTipo(tipo:String): List<Vehiculo> {
        var salida = dataBase.filter { it::class.simpleName == tipo }
        return salida
}

    override fun listarporModelo(tipo: String): List<Vehiculo> {
        var salida = dataBase.filter { it.modelo== tipo }
        return salida
    }
}