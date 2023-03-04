package FunctionalITV.repositories

import FunctionalITV.models.Car
import FunctionalITV.models.Vehicle
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ITVrepoTest {

    var repoITV = ITVrepo()

    @BeforeEach
    fun setup() {
        repoITV = ITVrepo()
    }

    @Test
    fun getAllTest() {
        val list: List<Vehicle> = repoITV.getAll()
        assertEquals(8, list.size)
        assertEquals(list[0].anyo, 1972)
        assertEquals(list[1].anyo, 1980)
        assertEquals(list[2].anyo, 1975)
        assertEquals(list[3].anyo, 1969)
        assertEquals(list[4].anyo, 2015)
        assertEquals(list[5].anyo, 2012)
        assertEquals(list[6].anyo, 1999)
        assertEquals(list[7].anyo, 2001)
    }

    @Test
    fun getOnlyCarsTest() {
        val list: List<Car> = repoITV.getOnlyCars()
        assertEquals(4, list.size)
        assertEquals(list[0].anyo, 1972)
        assertEquals(list[1].anyo, 1980)
        assertEquals(list[2].anyo, 1975)
        assertEquals(list[3].anyo, 1969)
    }

    @Test
    fun getMoreModernTest() {
        val vehicleMoreModern: Vehicle = repoITV.getMoreModern()
        assertEquals(2015, vehicleMoreModern.anyo)
    }

    @Test
    fun getLessKmTest() {
        val vehicleLessKm: Vehicle = repoITV.getLessKm()
        assertEquals(145, vehicleLessKm.km)
    }

    @Test
    fun searchByBrandTest() {
        val list: List<Vehicle> = repoITV.searchByBrand("Toyota")
        assertEquals(2, list.size)
        assertEquals(list[0].anyo, 1972)
        assertEquals(list[1].anyo, 1980)
    }

    @Test
    fun getAverageKmAllMotoTest() {
        val averageKmAllMoto = repoITV.getAverageKmAllMoto()
        assertEquals(236.25, averageKmAllMoto)
    }

    @Test
    fun getCarMoreAncientWithMoreTwoDoorsTest() {
        val car: Vehicle = repoITV.getCarMoreAncientWithMoreTwoDoors()
        assertEquals(1969, car.anyo)
    }

    @Test
    fun getNumByBrandTest() {
        val mapByBrandNumVehicle: Map<String, Int> = repoITV.getNumByBrand()
        assertEquals(6, mapByBrandNumVehicle.size)
        assertEquals(2, mapByBrandNumVehicle["Toyota"])
        assertEquals(1, mapByBrandNumVehicle["Citroen"])
        assertEquals(1, mapByBrandNumVehicle["Mazda"])
        assertEquals(2, mapByBrandNumVehicle["Yamaha"])
        assertEquals(1, mapByBrandNumVehicle["Honda"])
        assertEquals(1, mapByBrandNumVehicle["BMW"])
    }

    @Test
    fun getNumAptosByVehicleTest() {
        val mapByVehicleAptos: Map<String, Int> = repoITV.getNumAptosByVehicle()
        assertEquals(2, mapByVehicleAptos.size)
        assertEquals(3, mapByVehicleAptos["Car"])
        assertEquals(2, mapByVehicleAptos["Moto"])
    }

    @Test
    fun getByVehicleAverageYearFabricateTest() {
        val mapByVehicleAptos: Map<String, Int> = repoITV.getByVehicleAverageYearFabricate()
        assertEquals(2, mapByVehicleAptos.size)
        assertEquals(1974, mapByVehicleAptos["Car"])
        assertEquals(2007, mapByVehicleAptos["Moto"])
    }

    @Test
    fun getAllGroupByBrandTest() {
        val mapAllByBrand: Map<String, List<Vehicle>> = repoITV.getAllGroupByBrand()
        assertEquals(6, mapAllByBrand.size)
        assertEquals(1972, mapAllByBrand["Toyota"]!![0].anyo)
        assertEquals(1980, mapAllByBrand["Toyota"]!![1].anyo)
        assertEquals(1975, mapAllByBrand["Citroen"]!![0].anyo)
        assertEquals(1969, mapAllByBrand["Mazda"]!![0].anyo)
        assertEquals(2015, mapAllByBrand["Yamaha"]!![0].anyo)
        assertEquals(2012, mapAllByBrand["Yamaha"]!![1].anyo)
        assertEquals(1999, mapAllByBrand["Honda"]!![0].anyo)
        assertEquals(2001, mapAllByBrand["BMW"]!![0].anyo)
    }

    @Test
    fun getAllOrderByYearFabricateTest() {
        val list: List<Vehicle> = repoITV.getAllOrderByYearFabricate()
        assertEquals(8, list.size)
        assertEquals(list[0].anyo, 1969)
        assertEquals(list[1].anyo, 1972)
        assertEquals(list[2].anyo, 1975)
        assertEquals(list[3].anyo, 1980)
        assertEquals(list[4].anyo, 1999)
        assertEquals(list[5].anyo, 2001)
        assertEquals(list[6].anyo, 2012)
        assertEquals(list[7].anyo, 2015)
    }

    @Test
    fun getAllOrderByBrandDescTest() {
        val list: List<Vehicle> = repoITV.getAllOrderByBrandDesc()
        assertEquals(8, list.size)
        assertEquals(list[7].anyo, 2001)
        assertEquals(list[6].anyo, 1975)
        assertEquals(list[5].anyo, 1999)
        assertEquals(list[4].anyo, 1969)
        assertEquals(list[3].anyo, 1980)
        assertEquals(list[2].anyo, 1972)
        assertEquals(list[1].anyo, 2012)
        assertEquals(list[0].anyo, 2015)
    }

    @Test
    fun getAllGroupByBrandAndSortedByKmDescTest() {
        val mapAllByBrandSortedByKmDesc: Map<String, List<Vehicle>> = repoITV.getAllGroupByBrandAndSortedByKmDesc()
        assertEquals(6, mapAllByBrandSortedByKmDesc.size)
        assertEquals(1980, mapAllByBrandSortedByKmDesc["Toyota"]!![0].anyo)
        assertEquals(1972, mapAllByBrandSortedByKmDesc["Toyota"]!![1].anyo)
        assertEquals(1975, mapAllByBrandSortedByKmDesc["Citroen"]!![0].anyo)
        assertEquals(1969, mapAllByBrandSortedByKmDesc["Mazda"]!![0].anyo)
        assertEquals(2015, mapAllByBrandSortedByKmDesc["Yamaha"]!![0].anyo)
        assertEquals(2012, mapAllByBrandSortedByKmDesc["Yamaha"]!![1].anyo)
        assertEquals(1999, mapAllByBrandSortedByKmDesc["Honda"]!![0].anyo)
        assertEquals(2001, mapAllByBrandSortedByKmDesc["BMW"]!![0].anyo)
    }
}