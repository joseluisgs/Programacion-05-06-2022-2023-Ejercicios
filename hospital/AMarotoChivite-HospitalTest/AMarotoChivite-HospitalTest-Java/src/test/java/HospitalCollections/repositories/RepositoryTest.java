package HospitalCollections.repositories;

import HospitalCollections.base.IHospitalRepository;
import HospitalCollections.exceptions.FullRepositoryException;
import HospitalCollections.exceptions.PatientBadRequestException;
import HospitalCollections.exceptions.PatientNotFoundException;
import HospitalCollections.models.Patient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static HospitalCollections.utils.randomData.randomDni;
import static org.junit.jupiter.api.Assertions.*;

public abstract class RepositoryTest {

    // Interfaz comun de las implementaciones de todos los repositorios
    protected IHospitalRepository repoToEspecify;

    @Test
    void testGetAllReturnsAllPatients() throws PatientBadRequestException, FullRepositoryException {
        Patient patient1 = new Patient("12345678A", "John Doe", "1990-07-10", "2000-01-01", Patient.TypePatient.NORMAL);
        Patient patient2 = new Patient("87654321B", "Jane Doe", "1990-07-10", "2000-01-01", Patient.TypePatient.URGENT);
        repoToEspecify.save(patient1);
        repoToEspecify.save(patient2);

        List<Patient> allPatients = repoToEspecify.getAll();

        assertEquals(2, allPatients.size());
        assertTrue(allPatients.contains(patient1));
        assertTrue(allPatients.contains(patient2));
    }

    @Test
    void testSaveAndGetById() throws PatientBadRequestException, PatientNotFoundException, FullRepositoryException {
        Patient patient1 = new Patient("12345678A", "John Doe", "1990-07-10", "2000-01-01", Patient.TypePatient.NORMAL);
        repoToEspecify.save(patient1);

        Patient savedPatient = repoToEspecify.getById("12345678A");
        assertEquals(patient1, savedPatient);
    }

    @Test
    void testDeleteById() throws PatientNotFoundException, PatientBadRequestException, FullRepositoryException {
        Patient patient1 = new Patient("87654321B", "Jane Doe", "1990-07-10", "2000-01-01", Patient.TypePatient.URGENT);
        repoToEspecify.save(patient1);

        Patient deletedPatient = repoToEspecify.deleteById("87654321B");
        assertEquals(patient1, deletedPatient);
        // Verifico que se ha borrado nuevamente
        Patient deletedPatientAgain = repoToEspecify.deleteById("87654321B");
        assertNull(deletedPatientAgain);
    }

    @Test
    void testIsFull() throws PatientBadRequestException, FullRepositoryException {
        // Caso no está completo
        assertFalse(repoToEspecify.isFull());

        Patient patient1 = new Patient("87654321B", "Jane Doe", "1990-07-10", "2000-01-01", Patient.TypePatient.URGENT);
        repoToEspecify.save(patient1);
        assertFalse(repoToEspecify.isFull());

        // Caso está completo
        for (int i = 0; i < 11; i++) {
            String randomId = randomDni();
            repoToEspecify.save(new Patient(randomId, "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL));
        }
        assertTrue(repoToEspecify.isFull());
    }

    @Test
    void testGetNumPacients() throws PatientBadRequestException, FullRepositoryException {
        assertEquals(0, repoToEspecify.getNumPacients());

        Patient patient1 = new Patient("33333333C", "David Lee", "1990-07-10", "2000-01-01", Patient.TypePatient.NORMAL);
        repoToEspecify.save(patient1);
        assertEquals(1, repoToEspecify.getNumPacients());
    }

    @Test
    void testGetNumByType() throws PatientBadRequestException, FullRepositoryException {
        repoToEspecify.save(new Patient("123", "Juan", "1990-07-10", "2000-01-01", Patient.TypePatient.URGENT));
        repoToEspecify.save(new Patient("456", "Pedro", "1990-07-10", "2000-01-01", Patient.TypePatient.NORMAL));
        repoToEspecify.save(new Patient("789", "María", "1990-07-10", "2000-01-01", Patient.TypePatient.URGENT));

        // Índice 0 = Normal
        // Índice 0 = Urgente
        int[] counts = repoToEspecify.getNumByType();
        assertEquals(2, counts.length);
        assertEquals(1, counts[0]);
        assertEquals(2, counts[1]);
    }

    @Test
    void testGetAllOrderByEnterDate() throws PatientBadRequestException, FullRepositoryException {
        Patient patient1 = new Patient("11111111A", "Mike Smith", "1980-07-10", "2000-01-01", Patient.TypePatient.NORMAL);
        Patient patient2 = new Patient("22222222B", "Sarah Johnson", "1980-07-10", "2001-01-01", Patient.TypePatient.URGENT);
        // Primero introduzco el pacient2, ya que, es el que debería estar en la posición segunda posteriormente
        repoToEspecify.save(patient2);
        repoToEspecify.save(patient1);

        List<Patient> orderedList = repoToEspecify.getAllOrderByEnterDate();
        assertEquals(patient1, orderedList.get(0));
        assertEquals(patient2, orderedList.get(1));
    }

    @Test
    void testGetAllOrderByName() throws PatientBadRequestException, FullRepositoryException {
        Patient patient1 = new Patient("11111111A", "Mike Smith", "1980-07-10", "2000-01-01", Patient.TypePatient.NORMAL);
        Patient patient2 = new Patient("22222222B", "Sarah Johnson", "1980-07-10", "2001-01-01", Patient.TypePatient.URGENT);
        // Primero introduzco el pacient2, ya que, es el que debería estar en la posición segunda posteriormente
        repoToEspecify.save(patient2);
        repoToEspecify.save(patient1);

        List<Patient> orderedList = repoToEspecify.getAllOrderByName();
        assertEquals(patient1, orderedList.get(0));
        assertEquals(patient2, orderedList.get(1));
    }

    @Test
    void testGetAllOrderByType() throws PatientBadRequestException, FullRepositoryException {
        Patient patient1 = new Patient("11111111A", "Mike Smith", "1980-07-10", "2000-01-01", Patient.TypePatient.NORMAL);
        Patient patient2 = new Patient("22222222B", "Sarah Johnson", "1980-07-10", "2001-01-01", Patient.TypePatient.URGENT);
        // Primero introduzco el pacient2, ya que, es el que debería estar en la posición segunda posteriormente
        repoToEspecify.save(patient2);
        repoToEspecify.save(patient1);

        List<Patient> orderedList = repoToEspecify.getAllOrderByEnterDate();
        assertEquals(patient1, orderedList.get(0));
        assertEquals(patient2, orderedList.get(1));
    }
}

