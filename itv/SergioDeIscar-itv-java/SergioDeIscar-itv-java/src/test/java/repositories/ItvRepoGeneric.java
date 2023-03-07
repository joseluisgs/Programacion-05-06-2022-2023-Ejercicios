package repositories;

import org.example.interfaces.ItvExtension;
import org.example.models.Coche;
import org.example.models.Moto;
import org.example.models.Vehiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class ItvRepoGeneric {
    private ItvExtension repository;
    private ItvExtension repositoryShort;

    @BeforeEach
    void setUp() {
        repository = createRepoEmpty();
        repositoryShort = createRepoShort();
    }

    abstract ItvExtension createRepoEmpty();
    abstract ItvExtension createRepoShort();

    private final Vehiculo[] vehiculos = new Vehiculo[]{
            new Coche("1234ABC", "Audi", LocalDate.now().minusYears(5),
                    true, 150000, 2), // 0
            new Moto("1234ABD", "Ford", LocalDate.now().minusDays(40),
                    false, 24000, 600), // 1
            new Coche("1234ABE", "Pepe", LocalDate.now().minusYears(2),
                    true, 125000, 7), // 2
            new Moto("1234ABF", "Audi", LocalDate.now().minusDays(20),
                    true, 74000, 125) // 3
    };

    //region CRUD
    @Order(1)
    @Test
    void saveTest(){
        assertAll(
                () -> assertEquals(vehiculos[0], repository.save(vehiculos[0])),

                () -> assertEquals(vehiculos[0], repositoryShort.save(vehiculos[0])),
                () -> assertEquals(vehiculos[1], repositoryShort.save(vehiculos[1])),
                () -> assertEquals(vehiculos[2], repositoryShort.save(vehiculos[2])),
                () -> assertNull(repositoryShort.save(vehiculos[3]))
        );
    }

    @Order(2)
    @Test
    void saveAllTest(){
        repository.saveAll(List.of(vehiculos));
        repositoryShort.saveAll(List.of(vehiculos));
        assertAll(
                () -> assertArrayEquals(vehiculos, repository.findAll().toArray()),
                () -> assertArrayEquals(Arrays.copyOfRange(vehiculos, 0,3), repositoryShort.findAll().toArray())
        );
    }

    @Order(3)
    @Test
    void estaLlenoTest(){
        repository.saveAll(List.of(vehiculos));
        repositoryShort.saveAll(List.of(vehiculos));
        assertAll(
                () -> assertFalse(repository.isFull()),
                () -> assertTrue(repositoryShort.isFull())
        );
    }

    @Order(3)
    @Test
    void findTest(){
        repository.saveAll(List.of(Arrays.copyOfRange(vehiculos, 0,2)));
        assertAll(
                () -> assertEquals(vehiculos[0], repository.findById(vehiculos[0].getMatricula())),
                () -> assertEquals(vehiculos[1], repository.findById(vehiculos[1].getMatricula())),
                () -> assertNull(repository.findById(vehiculos[2].getMatricula()))
        );
    }

    @Order(3)
    @Test
    void deleteTest(){
        repository.saveAll(List.of(Arrays.copyOfRange(vehiculos, 0,2)));
        assertAll(
                () -> assertEquals(vehiculos[0], repository.deleteById(vehiculos[0].getMatricula())),
                () -> assertEquals(1, repository.findAll().size()),
                () -> assertEquals(vehiculos[1], repository.deleteById(vehiculos[1].getMatricula())),
                () -> assertNull(repository.deleteById(vehiculos[2].getMatricula()))
        );
    }
    //endregion

    //region Queries
    @Order(3)
    @Test
    void getCochesTest(){
        repository.saveAll(List.of(vehiculos));
        List<Vehiculo> response = repository.getCoches();
        assertAll(
                () -> assertEquals(vehiculos[0], response.get(0)),
                () -> assertEquals(vehiculos[2], response.get(1)),
                () -> assertEquals(2, response.size())
        );
    }

    @Order(3)
    @Test
    void getCocheMaxModernoTest(){
        repository.saveAll(List.of(vehiculos));
        assertEquals(vehiculos[2], repository.getCocheMaxModerno());
    }

    @Order(3)
    @Test
    void getVehiculoMinKilometrosTest(){
        repository.saveAll(List.of(vehiculos));
        assertEquals(vehiculos[1], repository.getVehiculoMinKilometros());
    }

    @Order(3)
    @Test
    void getMediaKilometrosMotosTest(){
        repository.saveAll(List.of(vehiculos));
        var expected = (double)(vehiculos[1].getKilometro() + vehiculos[3].getKilometro()) / 2;
        assertEquals(expected, repository.getMediaKilometrosMotos());
    }

    @Order(3)
    @Test
    void getOldestCocheTest(){
        repository.saveAll(List.of(vehiculos));
        assertEquals(vehiculos[0], repository.getOldestCoche());
    }

    @Order(3)
    @Test
    void getCountVehiculosTest(){
        repository.saveAll(List.of(vehiculos));
        var expected = Map.of("Coche", 2, "Moto", 2);
        assertEquals(expected, repository.getCountVehiculos());
    }

    @Order(3)
    @Test
    void getCountVehiculosAptosTest(){
        repository.saveAll(List.of(vehiculos));
        var expected = Map.of("Coche", 2, "Moto", 1);
        assertEquals(expected, repository.getCountVehiculosAptos());
    }

    @Order(3)
    @Test
    void getMediaVehiculosAnioTest(){
        repository.saveAll(List.of(vehiculos));
        var coche = ((double)(vehiculos[0].getAnio().getYear() + vehiculos[2].getAnio().getYear()) / 2);
        var moto = ((double)(vehiculos[1].getAnio().getYear() + vehiculos[3].getAnio().getYear()) / 2);
        var expected = Map.of("Coche", coche, "Moto", moto);
        assertEquals(expected, repository.getMediaVehiculosAnio());
    }

    @Order(3)
    @Test
    void groupVehiculosByMarcaTest(){
        repository.saveAll(List.of(vehiculos));
        var expected = Map.of(
                "Audi" , List.of(vehiculos[0], vehiculos[3]),
                "Ford", List.of(vehiculos[1]),
                "Pepe", List.of(vehiculos[2])
        );
        assertEquals(expected, repository.groupVehiculosByMarca());
    }

    @Order(3)
    @Test
    void sortVehiculosByAnioTest(){
        repository.saveAll(List.of(vehiculos));
        var expected = List.of(vehiculos[0], vehiculos[2], vehiculos[1], vehiculos[3]);
        assertEquals(expected, repository.sortVehiculosByAnio());
    }

    @Order(3)
    @Test
    void sortDesVehiculosByMarcaTest(){
        repository.saveAll(List.of(vehiculos));
        var expected = List.of(vehiculos[2], vehiculos[1], vehiculos[0], vehiculos[3]);
        assertEquals(expected, repository.sortDesVehiculosByMarca());
    }

    @Order(3)
    @Test
    void groupVehiculosByMarcaSortByKilometroTest(){
        repository.saveAll(List.of(vehiculos));
        var expected = Map.of(
                "Audi", List.of(vehiculos[3], vehiculos[0]),
                "Ford", List.of(vehiculos[1]),
                "Pepe", List.of(vehiculos[2])
        );
        assertEquals(expected, repository.groupVehiculosByMarcaSortByKilometro());
    }

    @Order(3)
    @Test
    void findByMarcaTest(){
        repository.saveAll(List.of(vehiculos));
        var expected = Map.of(
                "Audi", List.of(vehiculos[0], vehiculos[3]),
                "Ford", List.of(vehiculos[1]),
                "Pepe", List.of(vehiculos[2])
        );
        assertAll(
                () -> assertEquals(expected.get("Audi"), repository.findByMarca("Audi")),
                () -> assertEquals(expected.get("Ford"), repository.findByMarca("Ford")),
                () -> assertEquals(expected.get("Pepe"), repository.findByMarca("Pepe")),
                () -> assertEquals(Collections.emptyList(), repository.findByMarca("Pepe2"))
        );
    }

    @Order(3)
    @Test
    void getOldestCoche2PuertasTest(){
        repository.saveAll(List.of(vehiculos));
        assertEquals(vehiculos[2], repository.getOldestCoche2Puertas());
    }
    //endregion
}
