package org.example;

import models.Coche;
import models.Moto;
import models.Vehiculo;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.*;

public class Main {
    public static void main(String[] args) {
        var misVehiculos = List.of(
                new Coche("Seat", 135, 1975, true, 4),
                new Moto("Toyota", 12, 1845, false, 6),
                new Moto("Cytroen", 453, 2023, true, 12),
                new Coche("Toyota", 275, 1989, false, 2),
                new Moto("Peugeot", 157, 2014, true, 8),
                new Coche("Seat", 1256, 2004, false, 6)
        );

        //Mostrar todos los vehciulos:
        List<Vehiculo> lista = misVehiculos;
        lista.forEach(System.out::println);
        System.out.println();

        //mostrar solo coches:
        List<Vehiculo> lista1 = misVehiculos.stream().filter(v -> v instanceof Coche).toList();
        lista1.forEach(System.out::println);
        System.out.println();

        //mostrar vehículo más moderno:
        Vehiculo vehiculo = misVehiculos.stream().filter(v -> v instanceof Coche).max(Comparator.comparingInt(Vehiculo::getAñoMatriculacion)).get();
        System.out.println(vehiculo);
        System.out.println();

        //sacar vehículo con menos kilómetros:
        Vehiculo vehiculo1 = misVehiculos.stream().min(Comparator.comparingInt(Vehiculo::getKilometros)).get();
        System.out.println(vehiculo1);
        System.out.println();

        //buscar por marca
        List<Vehiculo> lista2 = misVehiculos.stream().filter(v -> v.getModelo().equals("Toyota")).toList();
        lista2.forEach(System.out::println);
        System.out.println();

        //sacar media kilómetros motos
        double mediaKilometrosMotos = misVehiculos.stream().filter(v -> v instanceof Moto).collect(averagingDouble(Vehiculo::getKilometros));
        System.out.println(mediaKilometrosMotos);
        System.out.println();

        //sacar coche más antiguo con más de dos puertas:
        Vehiculo vehiculo2 = misVehiculos.stream().filter(v -> v instanceof Coche && ((Coche) v).getNumeroPuertas() > 2)
                .min(Comparator.comparingInt(Vehiculo::getAñoMatriculacion)).get();
        System.out.println(vehiculo2);
        System.out.println();

        //obtener número de vehículos de cada tipo:
        Map<String, Long> mapa = misVehiculos.stream().collect(Collectors.groupingBy(v -> v instanceof Coche ? "Coches" : "Motos", Collectors.counting()));
        for(String key : mapa.keySet()){
            System.out.println(key + "---" + mapa.get(key));
        }
        System.out.println();

        //obtener número de vehículos de cada tipo que son aptos
        Map<String, Long> mapa1 = misVehiculos.stream().filter(Vehiculo::isApto).collect(Collectors.groupingBy(v -> v instanceof Coche ? "Coches" : "Motos", Collectors.counting()));
        for(String key : mapa1.keySet()){
            System.out.println(key + "---" + mapa1.get(key));
        }
        System.out.println();

        //obtener por cada tipo la media del año de fabricación
        Map<String, Double> mapa2 = misVehiculos.stream().collect(Collectors.groupingBy(v -> v instanceof Coche ? "Coches" : "Motos", Collectors.averagingDouble(Vehiculo::getAñoMatriculacion)));
        for(String key : mapa2.keySet()){
            System.out.println(key + "---" + mapa2.get(key));
        }
        System.out.println();

        //obtener vehículos agrupados por marca:
        Map<String, List<Vehiculo>> mapa3 = misVehiculos.stream().collect(Collectors.groupingBy(v -> v.getModelo()));
        for(String key : mapa3.keySet()){
            System.out.println(key + "---" + mapa3.get(key));
        }
        System.out.println();

        //mostrar todos los vehículos ordenados por año:
        List<Vehiculo> lista3 = misVehiculos.stream().sorted(Comparator.comparingInt(Vehiculo::getAñoMatriculacion)).toList();
        lista3.forEach(System.out::println);
        System.out.println();

        //obtener marcas ordenadas descendentemente:
        List<String> lista4 = misVehiculos.stream().map(Vehiculo::getModelo).distinct().sorted(Comparator.reverseOrder()).toList();
        lista4.forEach(System.out::println);
        System.out.println();

        //obtener los vehículos agrupados por marca y ordenados por kilómetros descendentemente:
        Map<String, List<Vehiculo>> mapa4 = misVehiculos.stream().sorted(Comparator.comparingInt(Vehiculo::getKilometros).reversed()).collect(Collectors.groupingBy(Vehiculo::getModelo));
        for(String key : mapa4.keySet()){
            System.out.println(key + "---" + mapa4.get(key));
        }
        System.out.println();
    }
}