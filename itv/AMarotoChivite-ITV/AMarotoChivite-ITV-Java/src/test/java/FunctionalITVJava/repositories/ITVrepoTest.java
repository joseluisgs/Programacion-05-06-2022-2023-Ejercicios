package FunctionalITVJava.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ITVrepoTest {

    ITVrepo repoITV = new ITVrepo();

    @BeforeEach
    void setUp() {
        repoITV = new ITVrepo();
    }

    @Test
    void getAllTest() {
        var list = repoITV.getAll();
        assertEquals(8, list.size());
        assertEquals(list.get(0).anyo, 1972);
        assertEquals(list.get(1).anyo, 1980);
        assertEquals(list.get(2).anyo, 1975);
        assertEquals(list.get(3).anyo, 1969);

        assertEquals(list.get(4).anyo, 2015);
        assertEquals(list.get(5).anyo, 2012);
        assertEquals(list.get(6).anyo, 1999);
        assertEquals(list.get(7).anyo, 2001);
    }

    @Test
    void getOnlyCarsTest() {
        var list = repoITV.getOnlyCars();
        assertEquals(4, list.size());
        assertEquals(list.get(0).anyo, 1972);
        assertEquals(list.get(1).anyo, 1980);
        assertEquals(list.get(2).anyo, 1975);
        assertEquals(list.get(3).anyo, 1969);
    }

    @Test
    void getMoreModernTest() {
        var vehicleMoreModern = repoITV.getMoreModern();
        assertEquals(2015, vehicleMoreModern.anyo);
    }

    @Test
    void getLessKmTest() {
        var vehicleLessKm = repoITV.getLessKm();
        assertEquals(145, vehicleLessKm.km);
    }

    @Test
    void searchByBrandTest() {
        var list = repoITV.searchByBrand("Toyota");
        assertEquals(2, list.size());
        assertEquals(list.get(0).anyo, 1972);
        assertEquals(list.get(1).anyo, 1980);
    }

    @Test
    void getAverageKmAllMotoTest() {
        var averageKmAllMoto = repoITV.getAverageKmAllMoto();
        assertEquals(236.25, averageKmAllMoto);
    }

    @Test
    void getCarMoreAncientWithMoreTwoDoorsTest() {
        var car = repoITV.getCarMoreAncientWithMoreTwoDoors();
        assertEquals(1969, car.anyo);
    }

    @Test
    void getNumByBrandTest() {
        var mapByBrandNumVehicle = repoITV.getNumByBrand();
        assertEquals(6, mapByBrandNumVehicle.size());
        assertEquals(2, mapByBrandNumVehicle.get("Toyota"));
        assertEquals(1, mapByBrandNumVehicle.get("Citroen"));
        assertEquals(1, mapByBrandNumVehicle.get("Mazda"));
        assertEquals(2, mapByBrandNumVehicle.get("Yamaha"));
        assertEquals(1, mapByBrandNumVehicle.get("Honda"));
        assertEquals(1, mapByBrandNumVehicle.get("BMW"));
    }

    @Test
    void getNumAptosByVehicleTest() {
        var mapByVehicleAptos = repoITV.getNumAptosByVehicle();
        assertEquals(2, mapByVehicleAptos.size());
        assertEquals(3, mapByVehicleAptos.get("Car"));
        assertEquals(2, mapByVehicleAptos.get("Moto"));
    }

    @Test
    void getByVehicleAverageYearFabricateTest() {
        var mapByVehicleAptos = repoITV.getByVehicleAverageYearFabricate();
        assertEquals(2, mapByVehicleAptos.size());
        assertEquals(1974, mapByVehicleAptos.get("Car"));
        assertEquals(2006.75, mapByVehicleAptos.get("Moto"));
    }

    @Test
    void getAllGroupByBrandTest() {
        var mapAllByBrand = repoITV.getAllGroupByBrand();
        assertEquals(6, mapAllByBrand.size());
        assertEquals(1972, mapAllByBrand.get("Toyota").get(0).anyo);
        assertEquals(1980, mapAllByBrand.get("Toyota").get(1).anyo);
        assertEquals(1975, mapAllByBrand.get("Citroen").get(0).anyo);
        assertEquals(1969, mapAllByBrand.get("Mazda").get(0).anyo);
        assertEquals(2015, mapAllByBrand.get("Yamaha").get(0).anyo);
        assertEquals(2012, mapAllByBrand.get("Yamaha").get(1).anyo);
        assertEquals(1999, mapAllByBrand.get("Honda").get(0).anyo);
        assertEquals(2001, mapAllByBrand.get("BMW").get(0).anyo);
    }

    @Test
    void getAllOrderByYearFabricateTest() {
        var list = repoITV.getAllOrderByYearFabricate();
        assertEquals(8, list.size());
        assertEquals(list.get(0).anyo, 1969);
        assertEquals(list.get(1).anyo, 1972);
        assertEquals(list.get(2).anyo, 1975);
        assertEquals(list.get(3).anyo, 1980);
        assertEquals(list.get(4).anyo, 1999);
        assertEquals(list.get(5).anyo, 2001);
        assertEquals(list.get(6).anyo, 2012);
        assertEquals(list.get(7).anyo, 2015);
    }

    @Test
    void getAllOrderByBrandDescTest() {
        var list = repoITV.getAllOrderByBrandDesc();
        assertEquals(8, list.size());
        assertEquals(list.get(7).anyo, 2001);
        assertEquals(list.get(6).anyo, 1975);
        assertEquals(list.get(5).anyo, 1999);
        assertEquals(list.get(4).anyo, 1969);
        assertEquals(list.get(3).anyo, 1980);
        assertEquals(list.get(2).anyo, 1972);
        assertEquals(list.get(1).anyo, 2012);
        assertEquals(list.get(0).anyo, 2015);
    }

    @Test
    void getAllGroupByBrandAndSortedByKmDescTest() {
        var mapAllByBrandSortedByKmDesc = repoITV.getAllGroupByBrandAndSortedByKmDesc();
        assertEquals(6, mapAllByBrandSortedByKmDesc.size());
        assertEquals(1980, mapAllByBrandSortedByKmDesc.get("Toyota").get(0).anyo);
        assertEquals(1972, mapAllByBrandSortedByKmDesc.get("Toyota").get(1).anyo);
        assertEquals(1975, mapAllByBrandSortedByKmDesc.get("Citroen").get(0).anyo);
        assertEquals(1969, mapAllByBrandSortedByKmDesc.get("Mazda").get(0).anyo);
        assertEquals(2015, mapAllByBrandSortedByKmDesc.get("Yamaha").get(0).anyo);
        assertEquals(2012, mapAllByBrandSortedByKmDesc.get("Yamaha").get(1).anyo);
        assertEquals(1999, mapAllByBrandSortedByKmDesc.get("Honda").get(0).anyo);
        assertEquals(2001, mapAllByBrandSortedByKmDesc.get("BMW").get(0).anyo);
    }
}