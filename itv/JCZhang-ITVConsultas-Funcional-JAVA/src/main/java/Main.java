import models.Coche;
import models.Moto;
import models.Vehicle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Coche v1 = new Coche("Toyota", 1985, true, 65.16, 1);
        Coche v2 = new Coche("Renault", 2023, false, 6552.98, 5);
        Coche v3 = new Coche("Peugeot", 1895, true, 1745.41, 9);
        Coche v4 = new Coche("Renault", 1785, false, 4565.47, 2);
        Coche v5 = new Coche("Ford", 2028, true, 56425.25, 6);
        Coche v6 = new Coche("Citroen", 2020, false, 4560.45, 4);
        Moto v7 = new Moto("Toyota", 1943, true, 1245.74, 8);
        Moto v8 = new Moto("Audi", 1235, false, 22.12, 35);
        Moto v9 = new Moto("Ferrari", 1865, false, 426.45, 15);
        Moto v10 = new Moto("Bugatti", 2025, true, 457.65, 2);
        List<Vehicle> lista = new ArrayList<>();
        lista.add(v1);
        lista.add(v2);
        lista.add(v3);
        lista.add(v4);
        lista.add(v5);
        lista.add(v6);
        lista.add(v7);
        lista.add(v8);
        lista.add(v9);
        lista.add(v10);


        selectAll(lista);    //Imprime toda la lista
        selectCoches(lista);   //Imprime solo los coches
        mostModernVehicle(lista);      // imprime el coche más moderno
        lessKilometerVehicle(lista);   // Imprime el vehiculo con menos kilómetros
        motoAverageKilometer(lista);  //hace la media de los kilómetros de las motos
        oldestCarWithMoreThan2Doors(lista);    // imprime el coche más antiguo ocn más de 2 puertas
        numOfVehiclesByType(lista);     // imprime el número de Vehículos por tipo
        numOfAptVehiclesByType(lista);     // Imprime el número de vehículos aptos por tipo
        averageYearByType(lista);      // Imprime la media de los años por tipo
        groupVehiclesByMarca(lista); //agrupa los vehículos por marca
        orderVehiclesByYear(lista);      // ordena los vehículos por año
        orderVehicleByMarcaDescending(lista);   //ordena los vehículos por marca descendientemente
        orderByNumKilometerGroupedVehiclesByMarca(lista);     //ordena por marca descendientemente los vehículos agrupados por núm. KM
    }

    public static List<Vehicle> selectAll(List<Vehicle> lista) {
        return lista;
    }

    public static List<Coche> selectCoches(List<Vehicle> lista) {
        return lista.stream().filter(it-> it instanceof Coche).map(it -> (Coche) it).toList();
    }

    public static Vehicle mostModernVehicle(List<Vehicle> lista) {
        return lista.stream().max(Comparator.comparing(Vehicle::getFabricationYear)).orElseThrow();
    }

    public static Vehicle lessKilometerVehicle(List<Vehicle> lista){
        return lista.stream().min(Comparator.comparing(Vehicle::getNumKm)).orElseThrow();
    }

    public static Map<String, List<Vehicle>> orderByNumKilometerGroupedVehiclesByMarca(List<Vehicle> lista) {
        return lista.stream().sorted(Comparator.comparing(Vehicle::getNumKm)).collect(Collectors.groupingBy(Vehicle::getMarca));
    }

    public static List<Vehicle> orderVehicleByMarcaDescending(List<Vehicle> lista) {
        return lista.stream().sorted(Comparator.comparing(Vehicle::getMarca)).toList();
    }

    public static List<Vehicle>   orderVehiclesByYear(List<Vehicle> lista){
        return lista.stream().sorted(Comparator.comparing(Vehicle::getFabricationYear)).toList();

    }
    public static Map<String, List<Vehicle>> groupVehiclesByMarca(List<Vehicle> lista) {
        return lista.stream().collect(Collectors.groupingBy(Vehicle::getMarca));
    }

    public static Map<String, Double> averageYearByType(List<Vehicle> lista){
        return lista.stream().collect(Collectors.groupingBy(it -> (it instanceof Moto) ? "Moto" : "Coche", Collectors.averagingDouble(it -> it.getFabricationYear())));


    }

    public static Map<String, Long> numOfAptVehiclesByType(List<Vehicle> lista){
        return lista.stream().collect(Collectors.groupingBy(it-> (it instanceof Moto) ? "Moto" : "Coche", Collectors.filtering(Vehicle::isApto, Collectors.counting())));
    }

    public static Map<String, Long> numOfVehiclesByType(List<Vehicle> lista) {
        return lista.stream().collect(Collectors.groupingBy(it -> (it instanceof Moto)? "Moto" : "Coche",Collectors.counting()));
    }

    public static Coche oldestCarWithMoreThan2Doors(List<Vehicle> lista) {
        return (Coche) lista.stream().filter(it -> it instanceof Coche).filter(it -> ((Coche) it).getNumPuertas() >2).min(Comparator.comparing(it-> it.getFabricationYear())).orElseThrow();
    }

    public static Double motoAverageKilometer(List<Vehicle> lista) {
        return lista.stream().filter(it -> it instanceof Moto).collect(Collectors.averagingDouble(Vehicle::getNumKm));
    }
}

