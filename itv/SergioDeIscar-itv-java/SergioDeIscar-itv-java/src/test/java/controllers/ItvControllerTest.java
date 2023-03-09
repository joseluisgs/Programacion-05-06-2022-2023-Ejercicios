package controllers;

import org.example.controllers.ItvController;
import org.example.exceptions.ItvExceptions;
import org.example.interfaces.ItvExtension;
import org.example.models.Coche;
import org.example.models.Moto;
import org.example.models.Vehiculo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@ExtendWith(MockitoException.class)
public class ItvControllerTest {
    @Mock
    private ItvExtension repo;
    @InjectMocks
    private ItvController controller;

    private final Vehiculo[] vehiculos = new Vehiculo[]{
            new Coche("1234ABC", "Audi", LocalDate.now().minusYears(5),
                    true, 150000, 2), // 0
            new Moto("AAAAAA", "Ford", LocalDate.now().minusDays(40),
                    false, 24000, 600), // 1 Mal matricula
            new Moto("9999BBB", "Ford", LocalDate.now().minusDays(40),
                    false, -1, 600), // 1 Mal kilometraje
            new Coche("1234ABE", "Pepe", LocalDate.now().minusYears(2),
                    true, 125000, -1), // 2 Mal puertas
            new Moto("1234ABF", "Audi", LocalDate.now().minusDays(20),
                    true, 74000, 90), // 3 Mal cilindrada
            new Coche("1234ABG", "Audi", LocalDate.of(1800, 1,1),
                    true, 74000, 90), // 4 Mal fecha
    };

/*    @BeforeEach
    void setUp() {
        controller = new ItvController(repo);
    }*/

    @Test
    void saveTest() {
        Mockito.when(repo.save(vehiculos[0])).thenReturn(vehiculos[0]);
        assertAll(//Comprobar excepciones
                () -> assertThrowsExactly(ItvExceptions.ItvMatriculaException.class, () -> controller.save(vehiculos[1])),
                () -> assertThrowsExactly(ItvExceptions.ItvKilometroException.class, () -> controller.save(vehiculos[2])),
                () -> assertThrowsExactly(ItvExceptions.ItvNumPuertasException.class, () -> controller.save(vehiculos[3])),
                () -> assertThrowsExactly(ItvExceptions.ItvCilindradaException.class, () -> controller.save(vehiculos[4])),
                () -> assertThrowsExactly(ItvExceptions.ItvAnioException.class, () -> controller.save(vehiculos[5])),
                () -> assertEquals(vehiculos[0], controller.save(vehiculos[0]))
        );
    }
}
