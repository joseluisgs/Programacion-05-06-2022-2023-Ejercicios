package HospitalCollections.controllers;

import HospitalCollections.base.IHospitalRepository;
import HospitalCollections.exceptions.FullRepositoryException;
import HospitalCollections.exceptions.PatientBadRequestException;
import HospitalCollections.exceptions.PatientNotFoundException;
import HospitalCollections.models.Patient;
import HospitalCollections.repositories.PatientListRepository;
import HospitalCollections.repositories.PatientMapRepository;
import HospitalCollections.repositories.PatientSetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static HospitalCollections.controllers.PatientController.TypeRepo.*;
import static HospitalCollections.utils.randomData.randomDni;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {

    @Mock
    private PatientListRepository repoList;
    @Mock
    private PatientSetRepository repoSet;
    @Mock
    private PatientMapRepository repoMap;
    @InjectMocks
    private PatientController patientController;

    // Preparo mi lista de repositorios para no repetir código
    private List<IHospitalRepository> patientRepositories;

    @BeforeEach
    public void setup() {
        patientRepositories = new ArrayList<>();
        patientRepositories.add(repoList);
        patientRepositories.add(repoSet);
        patientRepositories.add(repoMap);
        patientController = new PatientController(repoList, repoSet, repoMap);
    }

    @Test
    void testGetByIdCorrect() throws PatientNotFoundException, PatientBadRequestException, FullRepositoryException {
        // Test de Integración
        // Casos correctos

        Patient patient = new Patient("12345678A", "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL);

        for (IHospitalRepository repo : patientRepositories) {
            when(repo.getById(patient.getDni())).thenReturn(patient);
        }

        patientController.setTypeRepo(LIST); // Tenemos que especificar el tipo de repositorio que entrará
        Patient resultById1 = patientController.getById(patient.getDni());
        assertEquals(patient, resultById1);

        patientController.setTypeRepo(SET); // Tenemos que especificar el tipo de repositorio que entrará
        Patient resultById2 = patientController.getById(patient.getDni());
        assertEquals(patient, resultById2);

        patientController.setTypeRepo(MAP); // Tenemos que especificar el tipo de repositorio que entrará
        Patient resultById3 = patientController.getById(patient.getDni());
        assertEquals(patient, resultById3);

        for (IHospitalRepository repo : patientRepositories) {
            verify(repo, times(1)).getById(patient.getDni());
        }
    }

    @Test
    void testGetByIdInCorrect() throws PatientBadRequestException {
        // Test Unitario
        // Casos incorrectos

        // No introducimos objeto previamente, nunca lo encontrará
        Patient patientIncorrect = new Patient("12345678A", "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL);

        patientController.setTypeRepo(LIST); // Tenemos que especificar el tipo de repositorio que entrará
        var res = assertThrows(PatientNotFoundException.class, () -> patientController.getById(patientIncorrect.getDni()));
        assertEquals("El paciente con DNI " + patientIncorrect.getDni() + " no se encuentra en la base de datos.", res.getMessage());

        patientController.setTypeRepo(SET); // Tenemos que especificar el tipo de repositorio que entrará
        var res1 = assertThrows(PatientNotFoundException.class, () -> patientController.getById(patientIncorrect.getDni()));
        assertEquals("El paciente con DNI " + patientIncorrect.getDni() + " no se encuentra en la base de datos.", res1.getMessage());

        patientController.setTypeRepo(MAP); // Tenemos que especificar el tipo de repositorio que entrará
        var res2 = assertThrows(PatientNotFoundException.class, () -> patientController.getById(patientIncorrect.getDni()));
        assertEquals("El paciente con DNI " + patientIncorrect.getDni() + " no se encuentra en la base de datos.", res2.getMessage());
    }


    @Test
    void testSaveCorrect() throws PatientBadRequestException, FullRepositoryException {
        // Test de Integración
        // Casos correctos

        Patient patient = new Patient("12345678A", "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL);

        for (IHospitalRepository repo : patientRepositories) {
            when(repo.save(patient)).thenReturn(patient);
        }
        patientController.setTypeRepo(LIST); // Tenemos que especificar el tipo de repositorio que entrará
        Patient result1 = patientController.save(patient);
        assertEquals(patient, result1);

        patientController.setTypeRepo(SET); // Tenemos que especificar el tipo de repositorio que entrará
        Patient result2 = patientController.save(patient);
        assertEquals(patient, result2);

        patientController.setTypeRepo(MAP); // Tenemos que especificar el tipo de repositorio que entrará
        Patient result3 = patientController.save(patient);
        assertEquals(patient, result3);

        for (IHospitalRepository repo : patientRepositories) {
            verify(repo, times(1)).save(patient);
        }
    }

    @Test
    void testSaveInCorrect() {
        // Test Unitario
        // Casos incorrectos

        Patient patientIncorrect1 = new Patient("", "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL);
        Patient patientIncorrect2 = new Patient(null, "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL);
        Patient patientIncorrect3 = new Patient("123456789", "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL);
        Patient patientIncorrect4 = new Patient("1234567", "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL);
        Patient patientIncorrect5 = new Patient("A2345678A", "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL);

        Patient patientIncorrect6 = new Patient("12345678A", "", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL);
        Patient patientIncorrect7 = new Patient("12345678A", null, "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL);
        Patient patientIncorrect8 = new Patient("12345678A", "an", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL);
        Patient patientIncorrect9 = new Patient("12345678A", "abcdefghijklmnñstuvwx", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL);
        Patient patientIncorrect10 = new Patient("12345678A", "1234", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL);

        Patient patientIncorrect11 = new Patient("12345678A", "John Doe", "", "2000-01-01", Patient.TypePatient.NORMAL);
        Patient patientIncorrect12 = new Patient("12345678A", "John Doe", null, "2000-01-01", Patient.TypePatient.NORMAL);
        Patient patientIncorrect13 = new Patient("12345678A", "John Doe", "200-01-01", "2000-01-01", Patient.TypePatient.NORMAL);
        Patient patientIncorrect14 = new Patient("12345678A", "John Doe", "2000-0-01", "2000-01-01", Patient.TypePatient.NORMAL);
        Patient patientIncorrect15 = new Patient("12345678A", "John Doe", "2000-01-0", "2000-01-01", Patient.TypePatient.NORMAL);

        Patient patientIncorrect16 = new Patient("12345678A", "John Doe", "2000-01-01", "", Patient.TypePatient.NORMAL);
        Patient patientIncorrect17 = new Patient("12345678A", "John Doe", "2000-01-01", null, Patient.TypePatient.NORMAL);
        Patient patientIncorrect18 = new Patient("12345678A", "John Doe", "2000-01-01", "200-01-01", Patient.TypePatient.NORMAL);
        Patient patientIncorrect19 = new Patient("12345678A", "John Doe", "2000-01-01", "2000-0-01", Patient.TypePatient.NORMAL);
        Patient patientIncorrect20 = new Patient("12345678A", "John Doe", "2000-01-01", "2000-01-0", Patient.TypePatient.NORMAL);

        Patient patientIncorrect21 = new Patient("12345678A", "John Doe", "2000-01-01", "2000-01-01", null);

        for (IHospitalRepository repo : patientRepositories) {
            if (repo instanceof PatientListRepository) {
                patientController.setTypeRepo(LIST); // Tenemos que especificar el tipo de repositorio que entrará
                var res = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect1));
                assertEquals("El DNI no puede estar vacío", res.getMessage());
                var res1 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect2));
                assertEquals("El DNI no puede estar vacío", res1.getMessage());

                var res2 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect3));
                assertEquals("El DNI debe tener 8 número y una letra: (12345678A)!", res2.getMessage());
                var res3 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect4));
                assertEquals("El DNI debe tener 8 número y una letra: (12345678A)!", res3.getMessage());
                var res4 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect5));
                assertEquals("El DNI debe tener 8 número y una letra: (12345678A)!", res4.getMessage());

                var res6 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect6));
                assertEquals("El nombre no puede estar vacío", res6.getMessage());
                var res7 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect7));
                assertEquals("El nombre no puede estar vacío", res7.getMessage());

                var res8 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect8));
                assertEquals("El nombre debe tener entre 3 y 20 caracteres", res8.getMessage());
                var res9 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect9));
                assertEquals("El nombre debe tener entre 3 y 20 caracteres", res9.getMessage());
                var res10 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect10));
                assertEquals("El nombre debe tener entre 3 y 20 caracteres", res10.getMessage());

                var res11 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect11));
                assertEquals("La fecha de nacimiento no puede estar vacía", res11.getMessage());
                var res12 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect12));
                assertEquals("La fecha de nacimiento no puede estar vacía", res12.getMessage());

                var res13 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect13));
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res13.getMessage());
                var res14 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect14));
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res14.getMessage());
                var res15 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect15));
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res15.getMessage());

                var res16 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect16));
                assertEquals("La fecha de entrada no puede estar vacía", res16.getMessage());
                var res17 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect17));
                assertEquals("La fecha de entrada no puede estar vacía", res17.getMessage());

                var res18 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect18));
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res18.getMessage());
                var res19 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect19));
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res19.getMessage());
                var res20 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect20));
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res20.getMessage());

                var res21 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect21));
                assertEquals("El tipo del paciente no puede estar vacío", res21.getMessage());

            } else if (repo instanceof PatientSetRepository) {
                patientController.setTypeRepo(SET); // Tenemos que especificar el tipo de repositorio que entrará
                var res = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect1));
                assertEquals("El DNI no puede estar vacío", res.getMessage());
                var res1 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect2));
                assertEquals("El DNI no puede estar vacío", res1.getMessage());

                var res2 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect3));
                assertEquals("El DNI debe tener 8 número y una letra: (12345678A)!", res2.getMessage());
                var res3 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect4));
                assertEquals("El DNI debe tener 8 número y una letra: (12345678A)!", res3.getMessage());
                var res4 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect5));
                assertEquals("El DNI debe tener 8 número y una letra: (12345678A)!", res4.getMessage());

                var res6 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect6));
                assertEquals("El nombre no puede estar vacío", res6.getMessage());
                var res7 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect7));
                assertEquals("El nombre no puede estar vacío", res7.getMessage());

                var res8 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect8));
                assertEquals("El nombre debe tener entre 3 y 20 caracteres", res8.getMessage());
                var res9 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect9));
                assertEquals("El nombre debe tener entre 3 y 20 caracteres", res9.getMessage());
                var res10 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect10));
                assertEquals("El nombre debe tener entre 3 y 20 caracteres", res10.getMessage());

                var res11 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect11));
                assertEquals("La fecha de nacimiento no puede estar vacía", res11.getMessage());
                var res12 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect12));
                assertEquals("La fecha de nacimiento no puede estar vacía", res12.getMessage());

                var res13 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect13));
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res13.getMessage());
                var res14 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect14));
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res14.getMessage());
                var res15 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect15));
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res15.getMessage());

                var res16 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect16));
                assertEquals("La fecha de entrada no puede estar vacía", res16.getMessage());
                var res17 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect17));
                assertEquals("La fecha de entrada no puede estar vacía", res17.getMessage());

                var res18 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect18));
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res18.getMessage());
                var res19 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect19));
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res19.getMessage());
                var res20 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect20));
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res20.getMessage());

                var res21 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect21));
                assertEquals("El tipo del paciente no puede estar vacío", res21.getMessage());

            } else {
                patientController.setTypeRepo(MAP); // Tenemos que especificar el tipo de repositorio que entrará
                var res = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect1));
                assertEquals("El DNI no puede estar vacío", res.getMessage());
                var res1 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect2));
                assertEquals("El DNI no puede estar vacío", res1.getMessage());

                var res2 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect3));
                assertEquals("El DNI debe tener 8 número y una letra: (12345678A)!", res2.getMessage());
                var res3 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect4));
                assertEquals("El DNI debe tener 8 número y una letra: (12345678A)!", res3.getMessage());
                var res4 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect5));
                assertEquals("El DNI debe tener 8 número y una letra: (12345678A)!", res4.getMessage());

                var res6 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect6));
                assertEquals("El nombre no puede estar vacío", res6.getMessage());
                var res7 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect7));
                assertEquals("El nombre no puede estar vacío", res7.getMessage());

                var res8 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect8));
                assertEquals("El nombre debe tener entre 3 y 20 caracteres", res8.getMessage());
                var res9 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect9));
                assertEquals("El nombre debe tener entre 3 y 20 caracteres", res9.getMessage());
                var res10 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect10));
                assertEquals("El nombre debe tener entre 3 y 20 caracteres", res10.getMessage());

                var res11 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect11));
                assertEquals("La fecha de nacimiento no puede estar vacía", res11.getMessage());
                var res12 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect12));
                assertEquals("La fecha de nacimiento no puede estar vacía", res12.getMessage());

                var res13 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect13));
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res13.getMessage());
                var res14 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect14));
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res14.getMessage());
                var res15 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect15));
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res15.getMessage());

                var res16 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect16));
                assertEquals("La fecha de entrada no puede estar vacía", res16.getMessage());
                var res17 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect17));
                assertEquals("La fecha de entrada no puede estar vacía", res17.getMessage());

                var res18 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect18));
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res18.getMessage());
                var res19 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect19));
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res19.getMessage());
                var res20 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect20));
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res20.getMessage());

                var res21 = assertThrows(PatientBadRequestException.class, () -> patientController.save(patientIncorrect21));
                assertEquals("El tipo del paciente no puede estar vacío", res21.getMessage());

            }
        }
    }

    @Test
    void testDeleteByIdCorrect() throws PatientBadRequestException, PatientNotFoundException, FullRepositoryException {
        // Test de Integración
        // Casos correctos

        Patient patient = new Patient("12345678A", "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL);

        for (IHospitalRepository repo : patientRepositories) {
            when(repo.deleteById(patient.getDni())).thenReturn(patient);
        }
        patientController.setTypeRepo(LIST); // Tenemos que especificar el tipo de repositorio que entrará
        Patient result1 = patientController.deleteById(patient.getDni());
        assertEquals(patient, result1);

        patientController.setTypeRepo(SET); // Tenemos que especificar el tipo de repositorio que entrará
        Patient result2 = patientController.deleteById(patient.getDni());
        assertEquals(patient, result2);

        patientController.setTypeRepo(MAP); // Tenemos que especificar el tipo de repositorio que entrará
        Patient result3 = patientController.deleteById(patient.getDni());
        assertEquals(patient, result3);

        for (IHospitalRepository repo : patientRepositories) {
            verify(repo, times(1)).deleteById(patient.getDni());
        }
    }

    @Test
    void testDeleteByIdInCorrect() {
        // Test Unitario
        // Casos incorrectos

        // No introducimos el objeto, ya que forzamos que no lo encuentre
        Patient patientIncorrect = new Patient("12345678A", "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL);

        patientController.setTypeRepo(LIST); // Tenemos que especificar el tipo de repositorio que entrará
        var res = assertThrows(PatientNotFoundException.class, () -> patientController.getById(patientIncorrect.getDni()));
        assertEquals("El paciente con DNI " + patientIncorrect.getDni() + " no se encuentra en la base de datos.", res.getMessage());

        patientController.setTypeRepo(SET); // Tenemos que especificar el tipo de repositorio que entrará
        var res1 = assertThrows(PatientNotFoundException.class, () -> patientController.getById(patientIncorrect.getDni()));
        assertEquals("El paciente con DNI " + patientIncorrect.getDni() + " no se encuentra en la base de datos.", res1.getMessage());

        patientController.setTypeRepo(MAP); // Tenemos que especificar el tipo de repositorio que entrará
        var res2 = assertThrows(PatientNotFoundException.class, () -> patientController.getById(patientIncorrect.getDni()));
        assertEquals("El paciente con DNI " + patientIncorrect.getDni() + " no se encuentra en la base de datos.", res2.getMessage());
    }

    @Test
    void testIsFull() {
        // Caso está completo
        for (IHospitalRepository repo : patientRepositories) {
            when(repo.isFull()).thenReturn(true);
        }

        // Último objeto que hará lanzar la excepción de desbordamiento
        Patient patientIncorrect = new Patient("12345678A", "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL);

        patientController.setTypeRepo(LIST); // Tenemos que especificar el tipo de repositorio que entrará
        var res = assertThrows(FullRepositoryException.class, () -> patientController.save(patientIncorrect));
        assertEquals("La base de datos está llena, no se pueden guardar más pacientes.", res.getMessage());

        patientController.setTypeRepo(SET); // Tenemos que especificar el tipo de repositorio que entrará
        var res1 = assertThrows(FullRepositoryException.class, () -> patientController.save(patientIncorrect));
        assertEquals("La base de datos está llena, no se pueden guardar más pacientes.", res1.getMessage());

        patientController.setTypeRepo(MAP); // Tenemos que especificar el tipo de repositorio que entrará
        var res2 = assertThrows(FullRepositoryException.class, () -> patientController.save(patientIncorrect));
        assertEquals("La base de datos está llena, no se pueden guardar más pacientes.", res2.getMessage());

        for (IHospitalRepository repo : patientRepositories) {
            verify(repo, times(1)).isFull();
        }
    }

    @Test
    void testGetNum() {

        for (IHospitalRepository repo : patientRepositories) {
            when(repo.getNumPacients()).thenReturn(10);
        }

        patientController.setTypeRepo(LIST); // Tenemos que especificar el tipo de repositorio que entrará
        var res = patientController.getNumPacients();
        assertEquals(10, res);

        patientController.setTypeRepo(SET); // Tenemos que especificar el tipo de repositorio que entrará
        var res1 = patientController.getNumPacients();
        assertEquals(10, res1);

        patientController.setTypeRepo(MAP); // Tenemos que especificar el tipo de repositorio que entrará
        var res2 = patientController.getNumPacients();
        assertEquals(10, res2);

        for (IHospitalRepository repo : patientRepositories) {
            verify(repo, times(1)).getNumPacients();
        }
    }

    @Test
    void testGetNumByType() {
        int[] numByType = {3, 6};
        for (IHospitalRepository repo : patientRepositories) {
            when(repo.getNumByType()).thenReturn(numByType);
        }

        patientController.setTypeRepo(LIST); // Tenemos que especificar el tipo de repositorio que entrará
        int res = patientController.getNumByType()[0];
        int res1 = patientController.getNumByType()[1];
        assertEquals(numByType[0], res);
        assertEquals(numByType[1], res1);

        patientController.setTypeRepo(SET); // Tenemos que especificar el tipo de repositorio que entrará
        int res2 = patientController.getNumByType()[0];
        int res3 = patientController.getNumByType()[1];
        assertEquals(numByType[0], res2);
        assertEquals(numByType[1], res3);

        patientController.setTypeRepo(MAP); // Tenemos que especificar el tipo de repositorio que entrará
        int res4 = patientController.getNumByType()[0];
        int res5 = patientController.getNumByType()[1];
        assertEquals(numByType[0], res4);
        assertEquals(numByType[1], res5);

        for (IHospitalRepository repo : patientRepositories) {
            verify(repo, times(2)).getNumByType();
        }
    }

    @Test
    void testGetAll() {
        ArrayList<Patient> repoList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            String randomId = randomDni();
            repoList.add(new Patient(randomId, "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL));
        }

        for (IHospitalRepository repo : patientRepositories) {
            when(repo.getAll()).thenReturn(repoList);
        }

        patientController.setTypeRepo(LIST); // Tenemos que especificar el tipo de repositorio que entrará
        var res = patientController.getAll();
        assertEquals(repoList.get(0).getDni(), res.get(0).getDni());

        patientController.setTypeRepo(SET); // Tenemos que especificar el tipo de repositorio que entrará
        var res1 = patientController.getAll();
        assertEquals(repoList.get(0).getDni(), res1.get(0).getDni());

        patientController.setTypeRepo(MAP); // Tenemos que especificar el tipo de repositorio que entrará
        var res2 = patientController.getAll();
        assertEquals(repoList.get(0).getDni(), res2.get(0).getDni());

        for (IHospitalRepository repo : patientRepositories) {
            verify(repo, times(1)).getAll();
        }
    }

    @Test
    void testGetAllOrderByEnterDate() {
        ArrayList<Patient> sortedList = new ArrayList<>();
        sortedList.add(new Patient("12345678B", "Angel", "2000-01-01", "1000-01-01", Patient.TypePatient.NORMAL));
        sortedList.add(new Patient("12345678A", "Bartolo", "2000-01-01", "2000-01-01", Patient.TypePatient.URGENT));

        for (IHospitalRepository repo : patientRepositories) {
            when(repo.getAllOrderByEnterDate()).thenReturn(sortedList);
        }

        patientController.setTypeRepo(LIST); // Tenemos que especificar el tipo de repositorio que entrará
        List<Patient> res = patientController.getAllOrderByEnterDate();
        assertEquals(sortedList.get(0).getDni(), res.get(0).getDni());

        patientController.setTypeRepo(SET); // Tenemos que especificar el tipo de repositorio que entrará
        List<Patient> res1 = patientController.getAllOrderByEnterDate();
        assertEquals(sortedList.get(0).getDni(), res1.get(0).getDni());

        patientController.setTypeRepo(MAP); // Tenemos que especificar el tipo de repositorio que entrará
        List<Patient> res2 = patientController.getAllOrderByEnterDate();
        assertEquals(sortedList.get(0).getDni(), res2.get(0).getDni());

        for (IHospitalRepository repo : patientRepositories) {
            verify(repo, times(1)).getAllOrderByEnterDate();
        }
    }

    @Test
    void tesstGetAllOrderByName() {
        ArrayList<Patient> sortedList = new ArrayList<>();
        sortedList.add(new Patient("12345678B", "Angel", "2000-01-01", "1000-01-01", Patient.TypePatient.NORMAL));
        sortedList.add(new Patient("12345678A", "Bartolo", "2000-01-01", "2000-01-01", Patient.TypePatient.URGENT));

        for (IHospitalRepository repo : patientRepositories) {
            when(repo.getAllOrderByName()).thenReturn(sortedList);
        }

        patientController.setTypeRepo(LIST); // Tenemos que especificar el tipo de repositorio que entrará
        List<Patient> res = patientController.getAllOrderByName();
        assertEquals(sortedList.get(0).getDni(), res.get(0).getDni());

        patientController.setTypeRepo(SET); // Tenemos que especificar el tipo de repositorio que entrará
        List<Patient> res1 = patientController.getAllOrderByName();
        assertEquals(sortedList.get(0).getDni(), res1.get(0).getDni());

        patientController.setTypeRepo(MAP); // Tenemos que especificar el tipo de repositorio que entrará
        List<Patient> res2 = patientController.getAllOrderByName();
        assertEquals(sortedList.get(0).getDni(), res2.get(0).getDni());

        for (IHospitalRepository repo : patientRepositories) {
            verify(repo, times(1)).getAllOrderByName();
        }
    }

    @Test
    void TestGetAllOrderByType() {
        ArrayList<Patient> sortedList = new ArrayList<>();
        sortedList.add(new Patient("12345678B", "Angel", "2000-01-01", "1000-01-01", Patient.TypePatient.NORMAL));
        sortedList.add(new Patient("12345678A", "Bartolo", "2000-01-01", "2000-01-01", Patient.TypePatient.URGENT));

        for (IHospitalRepository repo : patientRepositories) {
            when(repo.getAllOrderByType()).thenReturn(sortedList);
        }

        patientController.setTypeRepo(LIST); // Tenemos que especificar el tipo de repositorio que entrará
        List<Patient> res = patientController.getAllOrderByType();
        assertEquals(sortedList.get(0).getDni(), res.get(0).getDni());

        patientController.setTypeRepo(SET); // Tenemos que especificar el tipo de repositorio que entrará
        List<Patient> res1 = patientController.getAllOrderByType();
        assertEquals(sortedList.get(0).getDni(), res1.get(0).getDni());

        patientController.setTypeRepo(MAP); // Tenemos que especificar el tipo de repositorio que entrará
        List<Patient> res2 = patientController.getAllOrderByType();
        assertEquals(sortedList.get(0).getDni(), res2.get(0).getDni());

        for (IHospitalRepository repo : patientRepositories) {
            verify(repo, times(1)).getAllOrderByType();
        }
    }
}



