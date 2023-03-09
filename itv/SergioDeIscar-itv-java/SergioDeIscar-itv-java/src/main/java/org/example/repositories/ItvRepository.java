package org.example.repositories;

import org.example.interfaces.ItvExtension;
import org.example.models.Coche;
import org.example.models.Moto;
import org.example.models.Vehiculo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ItvRepository implements ItvExtension {
    private final int maxSize;
    public ItvRepository() {
        this.maxSize = 10;
    }
    public ItvRepository(int maxSize) {
        this.maxSize = maxSize;
    }

    private final Map<String, Vehiculo> vehicles = new LinkedHashMap<>();
    @Override
    public Vehiculo save(Vehiculo entity) {
        if (isFull()) return null;
        vehicles.put(entity.getMatricula(), entity);
        return entity;
    }

    @Override
    public Vehiculo findById(String s) {
        return vehicles.get(s);
    }

    @Override
    public Vehiculo deleteById(String id) {
        return vehicles.remove(id);
    }

    @Override
    public List<Vehiculo> findAll() {
        return vehicles.values().stream().toList();
    }

    @Override
    public void saveAll(List<Vehiculo> entities) {
        if (isFull()) return;
        entities.forEach(v -> this.save(v));
    }

    @Override
    public Boolean isFull() {
        return vehicles.size() >= maxSize;
    }

    @Override
    public List<Vehiculo> getCoches() {
        return vehicles.values().stream().filter(v -> v instanceof Coche).toList();
    }

    @Override
    public Vehiculo getCocheMaxModerno() {
        return vehicles.values().stream().filter(v -> v instanceof Coche)
                .max((v1, v2) -> v1.getAnio().compareTo(v2.getAnio())).orElse(null);
    }

    @Override
    public Vehiculo getVehiculoMinKilometros() {
        return vehicles.values().stream().min((v1, v2) -> v1.getKilometro() - v2.getKilometro()).orElse(null);
    }

    @Override
    public List<Vehiculo> findByMarca(String marca) {
        return vehicles.values().stream().filter(v -> Objects.equals(v.getMarca(), marca)).toList();
    }

    @Override
    public Double getMediaKilometrosMotos() {
        return vehicles.values().stream().filter(v -> v instanceof Moto)
                .mapToDouble(v -> v.getKilometro()).average().orElse(0);
    }

    @Override
    public Vehiculo getOldestCoche() {
        return vehicles.values().stream().filter(v -> v instanceof Coche)
                .max((v1, v2) -> v2.getAnio().compareTo(v1.getAnio())).orElse(null);
    }

    @Override
    public Vehiculo getOldestCoche2Puertas() {
        return vehicles.values().stream().filter(v -> v instanceof Coche && ((Coche) v).getNumPuertas() > 2)
                .max((v1, v2) -> v2.getAnio().compareTo(v1.getAnio())).orElse(null);
    }

    @Override
    public Map<String, Integer> getCountVehiculos() {
        // return vehiculos.values.groupBy{ it::class.simpleName ?: "_" }.mapValues { it.value.size }
        return vehicles.values().stream().collect(
                Collectors.groupingBy(v -> v.getClass().getSimpleName(), Collectors.summingInt(v -> 1)));
    }

    @Override
    public Map<String, Integer> getCountVehiculosAptos() {
        return vehicles.values().stream().filter(v -> v.isApto())
                .collect(Collectors.groupingBy(v -> v.getClass().getSimpleName(), Collectors.summingInt(v -> 1)));
    }

    @Override
    public Map<String, Double> getMediaVehiculosAnio() {
        return vehicles.values().stream().collect(
                Collectors.groupingBy(v -> v.getClass().getSimpleName(), Collectors.averagingInt(v -> v.getAnio().getYear())));
    }

    @Override
    public Map<String, List<Vehiculo>> groupVehiculosByMarca() {
        return vehicles.values().stream().collect(Collectors.groupingBy(v -> v.getMarca()));
    }

    @Override
    public List<Vehiculo> sortVehiculosByAnio() {
        return vehicles.values().stream().sorted((v1, v2) -> v1.getAnio().compareTo(v2.getAnio())).toList();
    }

    @Override
    public List<Vehiculo> sortDesVehiculosByMarca() {
        return vehicles.values().stream().sorted((v1, v2) -> v2.getMarca().compareTo(v1.getMarca())).toList();
    }

    @Override
    public Map<String, List<Vehiculo>> groupVehiculosByMarcaSortByKilometro() {
        return vehicles.values().stream().sorted((v1, v2) -> v1.getKilometro() - v2.getKilometro())
                .collect(Collectors.groupingBy(v -> v.getMarca()));
    }
}
