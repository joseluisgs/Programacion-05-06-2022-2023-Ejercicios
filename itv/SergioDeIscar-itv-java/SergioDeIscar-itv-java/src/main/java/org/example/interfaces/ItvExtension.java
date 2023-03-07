package org.example.interfaces;

import org.example.models.Vehiculo;

import java.util.List;
import java.util.Map;

public interface ItvExtension extends CrudRepository<Vehiculo, String>{
    Boolean isFull();
    List<Vehiculo> getCoches();
    Vehiculo getCocheMaxModerno();
    Vehiculo getVehiculoMinKilometros();
    List<Vehiculo> findByMarca(String marca);
    Double getMediaKilometrosMotos();
    Vehiculo getOldestCoche();
    Vehiculo getOldestCoche2Puertas();
    Map<String, Integer> getCountVehiculos();
    Map<String, Integer> getCountVehiculosAptos();
    Map<String, Double> getMediaVehiculosAnio();
    Map<String, List<Vehiculo>> groupVehiculosByMarca();
    List<Vehiculo> sortVehiculosByAnio();
    List<Vehiculo> sortDesVehiculosByMarca();
    Map<String, List<Vehiculo>> groupVehiculosByMarcaSortByKilometro();
}
