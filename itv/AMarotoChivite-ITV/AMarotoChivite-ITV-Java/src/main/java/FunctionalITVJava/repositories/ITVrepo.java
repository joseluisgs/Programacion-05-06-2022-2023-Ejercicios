package FunctionalITVJava.repositories;

import FunctionalITVJava.base.ICrudITV;
import FunctionalITVJava.models.Car;
import FunctionalITVJava.models.Moto;
import FunctionalITVJava.models.Vehicle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.groupingBy;

public class ITVrepo implements ICrudITV<Vehicle> {

    private final ArrayList<Vehicle> repository = new ArrayList<>();

    public ITVrepo() {
        repository.add(new Car("Toyota", 1972, true, 320, 2));
        repository.add(new Car("Toyota", 1980, true, 330, 2));
        repository.add(new Car("Citroen", 1975, false, 200, 4));
        repository.add(new Car("Mazda", 1969, true, 500, 3));
        repository.add(new Moto("Yamaha", 2015, true, 150, 4));
        repository.add(new Moto("Yamaha", 2012, false, 145, 4));
        repository.add(new Moto("Honda", 1999, false, 350, 3));
        repository.add(new Moto("BMW", 2001, true, 300, 6));

    }

    @Override
    public List<Vehicle> getAll() {
        return repository;
    }

    @Override
    public List<Car> getOnlyCars() {
        return repository.stream().filter(it -> it instanceof Car).map(c -> (Car) c).toList();
    }

    @Override
    public Vehicle getMoreModern() {
        var listSortedByAnyo = repository.stream().sorted(Comparator.comparingInt(it -> it.anyo)).toList();
        return listSortedByAnyo.get(listSortedByAnyo.size() - 1);

        // Utilizando optional
        // return repository.stream().max(Comparator.comparingInt(it -> it.anyo)).get();
    }

    @Override
    public Vehicle getLessKm() {
        return repository.stream().sorted(Comparator.comparingInt(it -> it.km)).toList().get(0);

        // Utilizando optional
        // return repository.stream().min(Comparator.comparingInt(it -> it.km)).get();
    }

    @Override
    public List<Vehicle> searchByBrand(String brandToSearch) {
        return repository.stream().filter(it -> it.brand.equals(brandToSearch)).toList();
    }

    @Override
    public Double getAverageKmAllMoto() {
        var listKmMoto = repository.stream().filter(it -> it instanceof Moto).map(it -> it.km).toList();
        double sum = listKmMoto.stream().mapToInt(Integer::intValue).sum();
        return (sum / listKmMoto.size());
    }

    @Override
    public Vehicle getCarMoreAncientWithMoreTwoDoors() {
        var listSortedByAnyo = repository.stream().sorted(Comparator.comparingInt(it -> it.anyo)).toList();
        var carList = listSortedByAnyo.stream()
                .filter(it -> it instanceof Car && ((Car) it).numDoors > 2)
                .toList();
        return carList.get(0);
    }

    @Override
    public Map<String, Long> getNumByBrand() {
        return repository.stream()
                .collect(groupingBy(it -> it.brand, Collectors.counting()));
        // var mapGroupByBrand = repository.stream().collect(Collectors.<Vehicle, String>groupingBy(it -> it.brand,Collectors.counting()));
    }

    @Override
    public Map<String, Long> getNumAptosByVehicle() {
        var aptosList = repository.stream().filter(it -> it.apto);
        return aptosList.collect(groupingBy(it -> it.getClass().getSimpleName(), Collectors.counting()));
    }

    @Override
    public Map<String, Double> getByVehicleAverageYearFabricate() {
        return repository.stream()
                .collect(Collectors.groupingBy(vehicle -> vehicle.getClass().getSimpleName(), Collectors.averagingDouble(vehicle -> vehicle.anyo)));
    }

    @Override
    public Map<String, List<Vehicle>> getAllGroupByBrand() {
        return repository.stream()
                .collect(Collectors.groupingBy(it -> it.brand));
    }

    @Override
    public List<Vehicle> getAllOrderByYearFabricate() {
        return repository.stream()
                .sorted(Comparator.comparingInt(it -> it.anyo)).toList();
    }

    @Override
    public List<Vehicle> getAllOrderByBrandDesc() {
        return repository.stream()
                .sorted(Comparator.comparing(it -> it.brand, reverseOrder())).toList();
    }

    @Override
    public Map<String, List<Vehicle>> getAllGroupByBrandAndSortedByKmDesc() {
        return repository.stream()
                .sorted(Comparator.comparing(it -> it.km, reverseOrder()))
                .collect(Collectors.groupingBy(it -> it.brand));
    }
}