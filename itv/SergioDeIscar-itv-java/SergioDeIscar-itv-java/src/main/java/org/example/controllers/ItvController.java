package org.example.controllers;

import org.example.exceptions.ItvExceptions;
import org.example.interfaces.ItvExtension;
import org.example.models.Coche;
import org.example.models.Moto;
import org.example.models.Vehiculo;

import java.util.List;
import java.util.Map;

public class ItvController implements ItvExtension {
    private final ItvExtension repo;
    public ItvController(ItvExtension repo) {
        this.repo = repo;
    }
    @Override
    public Vehiculo save(Vehiculo entity) {
        checkVehiculo(entity);
        return repo.save(entity);
    }

    @Override
    public Vehiculo findById(String s) {
        checkMatricula(s);
        return repo.findById(s);
    }

    @Override
    public Vehiculo deleteById(String s) {
        checkMatricula(s);
        return repo.deleteById(s);
    }

    @Override
    public List<Vehiculo> findAll() {
        return repo.findAll();
    }

    @Override
    public void saveAll(List<Vehiculo> entities) {
        entities.forEach(v -> checkVehiculo(v));
        repo.saveAll(entities);
    }

    @Override
    public Boolean isFull() {
        return repo.isFull();
    }

    @Override
    public List<Vehiculo> getCoches() {
        return repo.getCoches();
    }

    @Override
    public Vehiculo getCocheMaxModerno() {
        return repo.getCocheMaxModerno();
    }

    @Override
    public Vehiculo getVehiculoMinKilometros() {
        return repo.getVehiculoMinKilometros();
    }

    @Override
    public List<Vehiculo> findByMarca(String marca) {
        return repo.findByMarca(marca);
    }

    @Override
    public Double getMediaKilometrosMotos() {
        return repo.getMediaKilometrosMotos();
    }

    @Override
    public Vehiculo getOldestCoche() {
        return repo.getOldestCoche();
    }

    @Override
    public Vehiculo getOldestCoche2Puertas() {
        return repo.getOldestCoche2Puertas();
    }

    @Override
    public Map<String, Integer> getCountVehiculos() {
        return repo.getCountVehiculos();
    }

    @Override
    public Map<String, Integer> getCountVehiculosAptos() {
        return repo.getCountVehiculosAptos();
    }

    @Override
    public Map<String, Double> getMediaVehiculosAnio() {
        return repo.getMediaVehiculosAnio();
    }

    @Override
    public Map<String, List<Vehiculo>> groupVehiculosByMarca() {
        return repo.groupVehiculosByMarca();
    }

    @Override
    public List<Vehiculo> sortVehiculosByAnio() {
        return repo.sortVehiculosByAnio();
    }

    @Override
    public List<Vehiculo> sortDesVehiculosByMarca() {
        return repo.sortDesVehiculosByMarca();
    }

    @Override
    public Map<String, List<Vehiculo>> groupVehiculosByMarcaSortByKilometro() {
        return repo.groupVehiculosByMarcaSortByKilometro();
    }

    private void checkVehiculo(Vehiculo vehiculo) {
        checkMatricula(vehiculo.getMatricula());
        if (checkNumber(vehiculo.getAnio().getYear(), 1900, 2023)) { throw new ItvExceptions.ItvAnioException(); }
        if(checkNumberMin(vehiculo.getKilometro(), 0)) { throw new ItvExceptions.ItvKilometroException(); }
        if (vehiculo instanceof Coche) {
            if (checkNumberMax(((Coche) vehiculo).getNumPuertas(), 7)) { throw new ItvExceptions.ItvNumPuertasException(); }
        }
        else if (vehiculo instanceof Moto) {
            if(checkNumberMin(((Moto) vehiculo).getCilindrada(), 100)) { throw new ItvExceptions.ItvCilindradaException(); }
        }
    }
    private void checkMatricula(String matricula) {
        if (!matricula.matches("[0-9]{4}[A-Z]{3}")) {
            throw new ItvExceptions.ItvMatriculaException();
        }
    }

    private Boolean checkNumberMax(int number, int max){
        return checkNumber(number, 0, max);
    }
    private Boolean checkNumberMin(int number, int min){
        return checkNumber(number, min, Integer.MAX_VALUE);
    }

    private Boolean checkNumber(int number, int min, int max){
        return number >= min && number <= max;
    }
}
