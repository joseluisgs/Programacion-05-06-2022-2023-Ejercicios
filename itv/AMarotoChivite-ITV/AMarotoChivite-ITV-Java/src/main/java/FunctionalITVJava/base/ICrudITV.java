package FunctionalITVJava.base;

import FunctionalITVJava.models.Car;
import FunctionalITVJava.models.Vehicle;

import java.util.List;
import java.util.Map;

public interface ICrudITV<T> {
    List<T> getAll();

    List<Car> getOnlyCars();

    T getMoreModern();

    T getLessKm();

    List<T> searchByBrand(String brandToSearch);

    Double getAverageKmAllMoto();

    T getCarMoreAncientWithMoreTwoDoors();

    Map<String, Long> getNumByBrand();

    Map<String, Long> getNumAptosByVehicle();

    Map<String, Double> getByVehicleAverageYearFabricate();

    Map<String, List<Vehicle>> getAllGroupByBrand();

    List<T> getAllOrderByYearFabricate();

    List<T> getAllOrderByBrandDesc();

    Map<String, List<Vehicle>> getAllGroupByBrandAndSortedByKmDesc();
}