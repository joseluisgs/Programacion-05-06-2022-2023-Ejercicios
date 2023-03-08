package repositories;

import org.example.enums.TipoPaciente;
import org.example.interfaces.HospitalExtension;
import org.example.models.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class HospitalRepoGeneric {
    private HospitalExtension repository;
    private HospitalExtension repositoryShort;

    @BeforeEach
    void setUp() {
        repository = createRepoEmpty();
        repositoryShort = createRepoShort();
    }

    abstract HospitalExtension createRepoEmpty();
    abstract HospitalExtension createRepoShort();

    private Paciente[] pacientes = new Paciente[]{
            new Paciente("30609298R", "Ztest", TipoPaciente.NORMAL),
            new Paciente("42298425E", "Atest", TipoPaciente.NORMAL),
            new Paciente("00972994W", "Etest", TipoPaciente.NORMAL),

            new Paciente("07932195R", "Ttest", TipoPaciente.URGENCIA),
            new Paciente("AAAAAAAAA", "Itest", TipoPaciente.URGENCIA), // DNI ERROR
    };


    @Order(1)
    @Test
    void saveTest(){
        assertAll(
            () -> assertEquals(pacientes[0].getDni(), repository.save(pacientes[0]).getDni()),

            () -> assertEquals(pacientes[0].getDni(), repositoryShort.save(pacientes[0]).getDni()),
            () -> assertEquals(pacientes[1].getDni(), repositoryShort.save(pacientes[1]).getDni()),
            () -> assertEquals(pacientes[2].getDni(), repositoryShort.save(pacientes[2]).getDni()),

            () -> assertNull(repositoryShort.save(pacientes[3])) // Está lleno
        );
    }

    @Order(2)
    @Test
    void saveAllTest(){
        repository.saveAll(List.of(pacientes));
        repositoryShort.saveAll(List.of(pacientes));
        assertAll(
            () -> assertArrayEquals(pacientes, repository.findAll().toArray()),
            () -> assertArrayEquals(Arrays.copyOfRange(pacientes, 0, 3), repositoryShort.findAll().toArray())
        );
    }

    @Order(3)
    @Test
    void estaLlenoTest(){
        repository.saveAll(List.of(pacientes));
        repositoryShort.saveAll(List.of(pacientes));
        assertAll(
                () -> assertFalse(repository.estaCompleto()),
                () -> assertTrue(repositoryShort.estaCompleto())
        );
    }

    @Order(3)
    @Test
    void findTest(){
        repository.save(pacientes[0]);
        repository.save(pacientes[1]);

        assertAll(
            () -> assertEquals(pacientes[0].getDni(), repository.findById(pacientes[0].getDni()).getDni()),
            () -> assertEquals(pacientes[1].getDni(), repository.findById(pacientes[1].getDni()).getDni()),
            () -> assertNull(repository.findById(pacientes[2].getDni()))
        );
    }

    @Order(3)
    @Test
    void deleteTest(){
        repository.save(pacientes[0]);
        repository.save(pacientes[1]);

        assertAll(
            () -> assertEquals(pacientes[0].getDni(), repository.deleteById(pacientes[0].getDni()).getDni()),
            () -> assertEquals(1, repository.numPacientes()),
            () -> assertEquals(pacientes[1].getDni(), repository.deleteById(pacientes[1].getDni()).getDni()),
            () -> assertEquals(0, repository.numPacientes()),
            () -> assertNull(repository.deleteById(pacientes[2].getDni()))
        );
    }

    @Order(3)
    @Test
    void numPacientesPorTipoTest(){
        repository.saveAll(List.of(pacientes));
        assertAll(
            () -> assertEquals(3, repository.numPacientePorTipo(TipoPaciente.NORMAL)),
            () -> assertEquals(2, repository.numPacientePorTipo(TipoPaciente.URGENCIA))
        );
    }

    @Order(3)
    @Test
    void pacientesPorTipoTest(){
        repository.saveAll(List.of(pacientes));
        assertAll(
            () -> assertArrayEquals(Arrays.copyOfRange(pacientes, 0, 3), repository.pacientesPorTipo(TipoPaciente.NORMAL).toArray()),
            () -> assertArrayEquals(Arrays.copyOfRange(pacientes, 3, 5), repository.pacientesPorTipo(TipoPaciente.URGENCIA).toArray())
        );
    }

    @Order(3)
    @Test
    void pacientesOrdeFechaIngresoTest(){
        repository.saveAll(List.of(pacientes));
        assertAll(
            () -> assertArrayEquals(pacientes, repository.pacientesOrdeFechaIngreso().toArray())
        );
    }

    @Order(3)
    @Test
    void pacientesOrdeNombreTest(){
        Paciente[] shortArray = new Paciente[]{
            pacientes[1],
            pacientes[2],
            pacientes[4],
            pacientes[3],
            pacientes[0]
        };
        repository.saveAll(List.of(pacientes));
        assertAll(
            () -> assertArrayEquals(shortArray, repository.pacientesOrdeNombre().toArray())
        );
    }

    @Order(3)
    @Test
    void pacienteOrdeDNITest(){
        Paciente[] shortArray = new Paciente[]{
            pacientes[2],
            pacientes[3],
            pacientes[0],
            pacientes[1],
            pacientes[4]
        };
        repository.saveAll(List.of(pacientes));
        assertAll(
            () -> assertArrayEquals(shortArray, repository.pacientesOrdeDNI().toArray())
        );
    }
}
