import controlers.ITVController
import models.Automovil
import repositories.ItvRepository

fun main(){
    var baseDDatos = ITVController(ItvRepository())
    println("  CONSULTAS")
    println("")
    println("1.- Mostrar todos los vehículos. lista")
     val consulta1 = baseDDatos.listarvehiculos()
    println(consulta1.toString())
    println("")
    println("2.- Mostrar solo coches. lista")
     val consulta2 = baseDDatos.listarporTipo("Automovil")
    println(consulta2.toString())
    println("")
    println("3.- Mostrar vehículo más moderno. vehículo")
     val consulta3= consulta1.maxBy { it.fechaMatriculacion }
    println(consulta3)
    println("")
    println("4.- Mostrar vehículo con menos kilómetros. vehículo")
    val consulta4= consulta1.minBy { it.kilometrosVehiculo }
    println(consulta4)
    println("")
    println("5.- Buscar por marca.lista")
     val consulta5= baseDDatos.listarporModelo("Corola")
    println(consulta5)
    println("")
    println("6.- Sacar media kilómetros motos. double.")
    val consulta6= baseDDatos.listarporTipo("Motocicleta")
    println("${(consulta6.sumOf { it?.kilometrosVehiculo?.toDouble() ?: 1.0}/consulta6.size.toDouble() )}")
    println("")
    println("7.- Sacar coche más antiguo con más de dos puertas. vehiculo..")
    val consulta7:List<Automovil> = baseDDatos.listarporTipo("Automovil") as List<Automovil>
    val consulta7final = consulta7.filter { it.numeroPlazas>2 }.minBy { it.fechaMatriculacion }
    println(consulta7final.toString())
    println("")
    println("8.- Obtener número de vehículos de cada tipo. mapa")
    val consulta8 = consulta1.groupBy { it.javaClass.simpleName }.mapValues { it.value.size }
    println(consulta8)
    println("")
    println("9.- Obtener número de vehículos de cada tipo que son aptos. mapa.")
    val consulta9= consulta1.filter { it.apto }.groupBy { it.javaClass.simpleName }.mapValues { it.value.size }
    println(consulta9.toString())
    println("")
    println("10.- Obtener vehículos agrupados por marca. mapa.")
    val consulta10= consulta1.map { it.modelo }
    println(consulta10)
    println("")
    println("11.- Mostrar todos los vehículos ordenados por año. lista")
    val consulta11= consulta1.toMutableList()
        consulta11.sortWith(){v1,v2-> v1.fechaMatriculacion compareTo v2.fechaMatriculacion}

    println(consulta11.toList())
    println("")
    println("12.- Obtener marcas ordenadas descendentemente. lista")
    val consulta12 = consulta1.toMutableList()
        consulta12.sortWith(){v1,v2-> v1.modelo compareTo v2.modelo}
        consulta12.reverse()
    var consulta12Nombres:List<String> = mutableListOf()
        for(Vehiculo in consulta12){
            consulta12Nombres+= Vehiculo.modelo
        }
    println(consulta12Nombres)
    println("")
    println("13.- Obtener los vehículos agrupados por marca y ordenados por kilómetros descendentemente. mapa.")
    val consulta13 = consulta1.groupBy { it.modelo }.mapValues { item ->item.value.sortedByDescending { it.kilometrosVehiculo }
        }
    println(consulta13.toList())
    println("")

    // obtener por cada tipo la media del año de fabricación. mapa.





}