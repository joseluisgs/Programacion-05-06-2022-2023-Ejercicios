package HospitalCollectionsTest.controllers

import HospitalCollections.base.IHospitalRepository
import HospitalCollections.controllers.PatientController
import HospitalCollections.exceptions.FullRepositoryException
import HospitalCollections.exceptions.PatientBadRequestException
import HospitalCollections.exceptions.PatientNotFoundException
import HospitalCollections.models.Patient
import HospitalCollections.repositories.PatientListRepository
import HospitalCollections.repositories.PatientMapRepository
import HospitalCollections.repositories.PatientSetRepository
import HospitalCollections.utils.randomData.randomDni
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class PatientControllerTest {

    @MockK
    private lateinit var repoList: PatientListRepository

    @MockK
    private lateinit var repoSet: PatientSetRepository

    @MockK
    private lateinit var repoMap: PatientMapRepository

    @InjectMockKs
    private lateinit var patientController: PatientController

    // Preparo mi lista de repositorios para no repetir código
    private lateinit var patientRepositories: MutableList<IHospitalRepository>

    @BeforeEach
    fun setUp() {
        patientRepositories = mutableListOf(repoList, repoSet, repoMap)
    }

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun testGetByIdCorrect() {
        // Casos correctos
        val patient = Patient("12345678A", "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL)
        for (repo in patientRepositories) {
            every { repo.getById(patient.getDni()) } returns patient
        }
        patientController.setTypeRepo(PatientController.TypeRepo.LIST) // Tenemos que especificar el tipo de repositorio que entrará
        val resultById1: Patient = patientController.getById(patient.getDni())
        assertEquals(patient, resultById1)

        patientController.setTypeRepo(PatientController.TypeRepo.SET) // Tenemos que especificar el tipo de repositorio que entrará
        val resultById2: Patient = patientController.getById(patient.getDni())
        assertEquals(patient, resultById2)

        patientController.setTypeRepo(PatientController.TypeRepo.MAP) // Tenemos que especificar el tipo de repositorio que entrará
        val resultById3: Patient = patientController.getById(patient.getDni())
        assertEquals(patient, resultById3)

        for (repo in patientRepositories) {
            verify { repo.getById(patient.getDni()) }
        }
    }

    @Test
    fun testGetByIdInCorrect() {
        // Casos incorrectos

        // No introducimos objeto previamente, nunca lo encontrará
        val dniToSearch = "12345678A"

        for (repo in patientRepositories) {
            every { repo.getById(dniToSearch) } returns null
        }

        patientController.setTypeRepo(PatientController.TypeRepo.LIST) // Tenemos que especificar el tipo de repositorio que entrará
        val res = assertThrows<PatientNotFoundException> { patientController.getById(dniToSearch) }
        assertEquals(
            "El paciente con DNI $dniToSearch no se encuentra en la base de datos.",
            res.message
        )

        patientController.setTypeRepo(PatientController.TypeRepo.SET) // Tenemos que especificar el tipo de repositorio que entrará
        val res1 = assertThrows<PatientNotFoundException> { patientController.getById(dniToSearch) }
        assertEquals(
            "El paciente con DNI $dniToSearch no se encuentra en la base de datos.",
            res1.message
        )

        patientController.setTypeRepo(PatientController.TypeRepo.MAP) // Tenemos que especificar el tipo de repositorio que entrará
        val res2 = assertThrows<PatientNotFoundException> { patientController.getById(dniToSearch) }
        assertEquals(
            "El paciente con DNI $dniToSearch no se encuentra en la base de datos.",
            res2.message
        )
    }

    @Test
    fun testSaveCorrect() {
        // Casos correctos
        val patient = Patient("12345678A", "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL)
        for (repo in patientRepositories) {
            every { repo.save(patient) } returns (patient)
            every { repo.isFull() } returns (false)
        }
        patientController.setTypeRepo(PatientController.TypeRepo.LIST) // Tenemos que especificar el tipo de repositorio que entrará
        val result1: Patient? = patientController.save(patient)
        assertEquals(patient, result1)

        patientController.setTypeRepo(PatientController.TypeRepo.SET) // Tenemos que especificar el tipo de repositorio que entrará
        val result2: Patient? = patientController.save(patient)
        assertEquals(patient, result2)

        patientController.setTypeRepo(PatientController.TypeRepo.MAP) // Tenemos que especificar el tipo de repositorio que entrará
        val result3: Patient? = patientController.save(patient)

        assertEquals(patient, result3)
        for (repo in patientRepositories) {
            verify { repo.save(patient) }
        }
    }

    @Test
    fun testSaveInCorrect() {
        // Casos incorrectos
        val patientIncorrect1 = Patient("", "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL)
        val patientIncorrect2 = Patient(null, "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL)
        val patientIncorrect3 = Patient("123456789", "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL)
        val patientIncorrect4 = Patient("1234567", "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL)
        val patientIncorrect5 = Patient("A2345678A", "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL)
        val patientIncorrect6 = Patient("12345678A", "", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL)
        val patientIncorrect7 = Patient("12345678A", null, "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL)
        val patientIncorrect8 = Patient("12345678A", "an", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL)
        val patientIncorrect9 =
            Patient("12345678A", "abcdefghijklmnñstuvwx", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL)
        val patientIncorrect10 = Patient("12345678A", "1234", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL)
        val patientIncorrect11 = Patient("12345678A", "John Doe", "", "2000-01-01", Patient.TypePatient.NORMAL)
        val patientIncorrect12 = Patient("12345678A", "John Doe", null, "2000-01-01", Patient.TypePatient.NORMAL)
        val patientIncorrect13 = Patient("12345678A", "John Doe", "200-01-01", "2000-01-01", Patient.TypePatient.NORMAL)
        val patientIncorrect14 = Patient("12345678A", "John Doe", "2000-0-01", "2000-01-01", Patient.TypePatient.NORMAL)
        val patientIncorrect15 = Patient("12345678A", "John Doe", "2000-01-0", "2000-01-01", Patient.TypePatient.NORMAL)
        val patientIncorrect16 = Patient("12345678A", "John Doe", "2000-01-01", "", Patient.TypePatient.NORMAL)
        val patientIncorrect17 = Patient("12345678A", "John Doe", "2000-01-01", null, Patient.TypePatient.NORMAL)
        val patientIncorrect18 = Patient("12345678A", "John Doe", "2000-01-01", "200-01-01", Patient.TypePatient.NORMAL)
        val patientIncorrect19 = Patient("12345678A", "John Doe", "2000-01-01", "2000-0-01", Patient.TypePatient.NORMAL)
        val patientIncorrect20 = Patient("12345678A", "John Doe", "2000-01-01", "2000-01-0", Patient.TypePatient.NORMAL)
        val patientIncorrect21 = Patient("12345678A", "John Doe", "2000-01-01", "2000-01-01", null)

        for (repo in patientRepositories) {
            if (repo is PatientListRepository) {
                patientController.setTypeRepo(PatientController.TypeRepo.LIST) // Tenemos que especificar el tipo de repositorio que entrará

                val res = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect1) }
                assertEquals("El DNI no puede estar vacío", res.message)

                val res1 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect2) }
                assertEquals("El DNI no puede estar vacío", res1.message)

                val res2 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect3) }
                assertEquals("El DNI debe tener 8 número y una letra: (12345678A)!", res2.message)

                val res3 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect4) }
                assertEquals("El DNI debe tener 8 número y una letra: (12345678A)!", res3.message)

                val res4 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect5) }
                assertEquals("El DNI debe tener 8 número y una letra: (12345678A)!", res4.message)

                val res6 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect6) }
                assertEquals("El nombre no puede estar vacío", res6.message)

                val res7 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect7) }
                assertEquals("El nombre no puede estar vacío", res7.message)

                val res8 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect8) }
                assertEquals("El nombre debe tener entre 3 y 20 caracteres", res8.message)

                val res9 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect9) }
                assertEquals("El nombre debe tener entre 3 y 20 caracteres", res9.message)

                val res10 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect10) }
                assertEquals("El nombre debe tener entre 3 y 20 caracteres", res10.message)

                val res11 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect11) }
                assertEquals("La fecha de nacimiento no puede estar vacía", res11.message)

                val res12 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect12) }
                assertEquals("La fecha de nacimiento no puede estar vacía", res12.message)

                val res13 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect13) }
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res13.message)

                val res14 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect14) }
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res14.message)

                val res15 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect15) }
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res15.message)

                val res16 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect16) }
                assertEquals("La fecha de entrada no puede estar vacía", res16.message)

                val res17 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect17) }
                assertEquals("La fecha de entrada no puede estar vacía", res17.message)

                val res18 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect18) }
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res18.message)

                val res19 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect19) }
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res19.message)

                val res20 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect20) }
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res20.message)

                val res21 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect21) }
                assertEquals("El tipo del paciente no puede estar vacío", res21.message)

            } else if (repo is PatientSetRepository) {
                patientController.setTypeRepo(PatientController.TypeRepo.SET) // Tenemos que especificar el tipo de repositorio que entrará

                val res = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect1) }
                assertEquals("El DNI no puede estar vacío", res.message)

                val res1 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect2) }
                assertEquals("El DNI no puede estar vacío", res1.message)

                val res2 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect3) }
                assertEquals("El DNI debe tener 8 número y una letra: (12345678A)!", res2.message)

                val res3 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect4) }
                assertEquals("El DNI debe tener 8 número y una letra: (12345678A)!", res3.message)

                val res4 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect5) }
                assertEquals("El DNI debe tener 8 número y una letra: (12345678A)!", res4.message)

                val res6 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect6) }
                assertEquals("El nombre no puede estar vacío", res6.message)

                val res7 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect7) }
                assertEquals("El nombre no puede estar vacío", res7.message)

                val res8 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect8) }
                assertEquals("El nombre debe tener entre 3 y 20 caracteres", res8.message)

                val res9 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect9) }
                assertEquals("El nombre debe tener entre 3 y 20 caracteres", res9.message)

                val res10 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect10) }
                assertEquals("El nombre debe tener entre 3 y 20 caracteres", res10.message)

                val res11 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect11) }
                assertEquals("La fecha de nacimiento no puede estar vacía", res11.message)

                val res12 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect12) }
                assertEquals("La fecha de nacimiento no puede estar vacía", res12.message)

                val res13 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect13) }
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res13.message)

                val res14 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect14) }
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res14.message)

                val res15 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect15) }
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res15.message)

                val res16 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect16) }
                assertEquals("La fecha de entrada no puede estar vacía", res16.message)

                val res17 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect17) }
                assertEquals("La fecha de entrada no puede estar vacía", res17.message)

                val res18 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect18) }
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res18.message)

                val res19 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect19) }
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res19.message)

                val res20 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect20) }
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res20.message)

                val res21 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect21) }
                assertEquals("El tipo del paciente no puede estar vacío", res21.message)

            } else {
                patientController.setTypeRepo(PatientController.TypeRepo.MAP) // Tenemos que especificar el tipo de repositorio que entrará

                val res = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect1) }
                assertEquals("El DNI no puede estar vacío", res.message)

                val res1 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect2) }
                assertEquals("El DNI no puede estar vacío", res1.message)

                val res2 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect3) }
                assertEquals("El DNI debe tener 8 número y una letra: (12345678A)!", res2.message)

                val res3 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect4) }
                assertEquals("El DNI debe tener 8 número y una letra: (12345678A)!", res3.message)

                val res4 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect5) }
                assertEquals("El DNI debe tener 8 número y una letra: (12345678A)!", res4.message)

                val res6 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect6) }
                assertEquals("El nombre no puede estar vacío", res6.message)

                val res7 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect7) }
                assertEquals("El nombre no puede estar vacío", res7.message)

                val res8 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect8) }
                assertEquals("El nombre debe tener entre 3 y 20 caracteres", res8.message)

                val res9 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect9) }
                assertEquals("El nombre debe tener entre 3 y 20 caracteres", res9.message)

                val res10 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect10) }
                assertEquals("El nombre debe tener entre 3 y 20 caracteres", res10.message)

                val res11 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect11) }
                assertEquals("La fecha de nacimiento no puede estar vacía", res11.message)

                val res12 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect12) }
                assertEquals("La fecha de nacimiento no puede estar vacía", res12.message)

                val res13 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect13) }
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res13.message)

                val res14 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect14) }
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res14.message)

                val res15 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect15) }
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res15.message)

                val res16 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect16) }
                assertEquals("La fecha de entrada no puede estar vacía", res16.message)

                val res17 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect17) }
                assertEquals("La fecha de entrada no puede estar vacía", res17.message)

                val res18 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect18) }
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res18.message)

                val res19 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect19) }
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res19.message)

                val res20 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect20) }
                assertEquals("La fecha debe estar en formato (aaaa-mm-dd)", res20.message)

                val res21 = assertThrows<PatientBadRequestException> { patientController.save(patientIncorrect21) }
                assertEquals("El tipo del paciente no puede estar vacío", res21.message)
            }
        }
    }

    @Test
    fun testDeleteByIdCorrect() {
        // Casos correctos
        val patient = Patient("12345678A", "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL)
        for (repo in patientRepositories) {
            every { repo.deleteById(patient.getDni()) } returns (patient)
        }
        patientController.setTypeRepo(PatientController.TypeRepo.LIST) // Tenemos que especificar el tipo de repositorio que entrará
        val result1: Patient = patientController.deleteById(patient.getDni())
        assertEquals(patient, result1)

        patientController.setTypeRepo(PatientController.TypeRepo.SET) // Tenemos que especificar el tipo de repositorio que entrará
        val result2: Patient = patientController.deleteById(patient.getDni())
        assertEquals(patient, result2)

        patientController.setTypeRepo(PatientController.TypeRepo.MAP) // Tenemos que especificar el tipo de repositorio que entrará
        val result3: Patient = patientController.deleteById(patient.getDni())
        assertEquals(patient, result3)

        for (repo in patientRepositories) {
            verify { repo.deleteById(patient.getDni()) }
        }
    }

    @Test
    fun testDeleteByIdInCorrect() {
        // Casos incorrectos

        // No introducimos el objeto, ya que forzamos que no lo encuentre
        val patientIncorrect = Patient("12345678A", "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL)

        patientController.setTypeRepo(PatientController.TypeRepo.LIST) // Tenemos que especificar el tipo de repositorio que entrará
        val res = assertThrows<PatientNotFoundException> { patientController.getById(patientIncorrect.getDni()) }
        assertEquals(
            "El paciente con DNI " + patientIncorrect.getDni() + " no se encuentra en la base de datos.",
            res.message
        )

        patientController.setTypeRepo(PatientController.TypeRepo.SET) // Tenemos que especificar el tipo de repositorio que entrará
        val res1 = assertThrows<PatientNotFoundException> { patientController.getById(patientIncorrect.getDni()) }
        assertEquals(
            "El paciente con DNI " + patientIncorrect.getDni() + " no se encuentra en la base de datos.",
            res1.message
        )

        patientController.setTypeRepo(PatientController.TypeRepo.MAP) // Tenemos que especificar el tipo de repositorio que entrará
        val res2 = assertThrows<PatientNotFoundException> { patientController.getById(patientIncorrect.getDni()) }
        assertEquals(
            "El paciente con DNI " + patientIncorrect.getDni() + " no se encuentra en la base de datos.",
            res2.message
        )
    }

    @Test
    fun testIsFull() {
        // Caso está completo
        for (repo in patientRepositories) {
            every { repo.isFull() } returns (true)
        }

        // Último objeto que hará lanzar la excepción de desbordamiento
        val patientIncorrect = Patient("12345678A", "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL)

        patientController.setTypeRepo(PatientController.TypeRepo.LIST) // Tenemos que especificar el tipo de repositorio que entrará
        val res = assertThrows<FullRepositoryException> { patientController.save(patientIncorrect) }
        assertEquals("La base de datos está llena, no se pueden guardar más pacientes.", res.message)

        patientController.setTypeRepo(PatientController.TypeRepo.SET) // Tenemos que especificar el tipo de repositorio que entrará
        val res1 = assertThrows<FullRepositoryException> { patientController.save(patientIncorrect) }
        assertEquals("La base de datos está llena, no se pueden guardar más pacientes.", res1.message)

        patientController.setTypeRepo(PatientController.TypeRepo.MAP) // Tenemos que especificar el tipo de repositorio que entrará
        val res2 = assertThrows<FullRepositoryException> { patientController.save(patientIncorrect) }
        assertEquals("La base de datos está llena, no se pueden guardar más pacientes.", res2.message)

        for (repo in patientRepositories) {
            verify { repo.isFull() }
        }
    }

    @Test
    fun testGetNum() {
        for (repo in patientRepositories) {
            every { repo.numPacients } returns (10)
        }
        patientController.setTypeRepo(PatientController.TypeRepo.LIST) // Tenemos que especificar el tipo de repositorio que entrará
        val res = patientController.numPacients
        assertEquals(10, res)

        patientController.setTypeRepo(PatientController.TypeRepo.SET) // Tenemos que especificar el tipo de repositorio que entrará
        val res1 = patientController.numPacients
        assertEquals(10, res1)

        patientController.setTypeRepo(PatientController.TypeRepo.MAP) // Tenemos que especificar el tipo de repositorio que entrará
        val res2 = patientController.numPacients
        assertEquals(10, res2)

        for (repo in patientRepositories) {
            verify { repo.numPacients }
        }
    }

    @Test
    fun testGetNumByType() {
        val numByType = intArrayOf(3, 6)
        for (repo in patientRepositories) {
            every { repo.numByType } returns (numByType)
        }
        patientController.setTypeRepo(PatientController.TypeRepo.LIST) // Tenemos que especificar el tipo de repositorio que entrará
        val res = patientController.numByType[0]
        val res1 = patientController.numByType[1]
        assertEquals(numByType[0], res)
        assertEquals(numByType[1], res1)

        patientController.setTypeRepo(PatientController.TypeRepo.SET) // Tenemos que especificar el tipo de repositorio que entrará
        val res2 = patientController.numByType[0]
        val res3 = patientController.numByType[1]
        assertEquals(numByType[0], res2)
        assertEquals(numByType[1], res3)

        patientController.setTypeRepo(PatientController.TypeRepo.MAP) // Tenemos que especificar el tipo de repositorio que entrará
        val res4 = patientController.numByType[0]
        val res5 = patientController.numByType[1]
        assertEquals(numByType[0], res4)
        assertEquals(numByType[1], res5)

        for (repo in patientRepositories) {
            verify(exactly = 2) { repo.numByType }
        }
    }

    @Test
    fun testGetAll() {
        val repoList: ArrayList<Patient> = ArrayList<Patient>()
        for (i in 0..2) {
            val randomId: String = randomDni()
            repoList.add(Patient(randomId, "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL))
        }

        for (repo in patientRepositories) {
            every { repo.getAll() } returns (repoList)
        }

        patientController.setTypeRepo(PatientController.TypeRepo.LIST) // Tenemos que especificar el tipo de repositorio que entrará
        val res = patientController.getAll()
        assertEquals(repoList[0].getDni(), res.get(0)!!.getDni())

        patientController.setTypeRepo(PatientController.TypeRepo.SET) // Tenemos que especificar el tipo de repositorio que entrará
        val res1 = patientController.getAll()
        assertEquals(repoList[0].getDni(), res1.get(0)!!.getDni())

        patientController.setTypeRepo(PatientController.TypeRepo.MAP) // Tenemos que especificar el tipo de repositorio que entrará
        val res2 = patientController.getAll()
        assertEquals(repoList[0].getDni(), res2.get(0)!!.getDni())

        for (repo in patientRepositories) {
            verify { repo.getAll() }
        }
    }

    @Test
    fun testGetAllOrderByEnterDate() {
        val sortedList: ArrayList<Patient> = ArrayList<Patient>()
        sortedList.add(Patient("12345678B", "Angel", "2000-01-01", "1000-01-01", Patient.TypePatient.NORMAL))
        sortedList.add(Patient("12345678A", "Bartolo", "2000-01-01", "2000-01-01", Patient.TypePatient.URGENT))

        for (repo in patientRepositories) {
            every { repo.allOrderByEnterDate } returns (sortedList)
        }

        patientController.setTypeRepo(PatientController.TypeRepo.LIST) // Tenemos que especificar el tipo de repositorio que entrará
        val res: List<Patient?> = patientController.allOrderByEnterDate
        assertEquals(sortedList[0].getDni(), res[0]!!.getDni())

        patientController.setTypeRepo(PatientController.TypeRepo.SET) // Tenemos que especificar el tipo de repositorio que entrará
        val res1: List<Patient?> = patientController.allOrderByEnterDate
        assertEquals(sortedList[0].getDni(), res1[0]!!.getDni())

        patientController.setTypeRepo(PatientController.TypeRepo.MAP) // Tenemos que especificar el tipo de repositorio que entrará
        val res2: List<Patient?> = patientController.allOrderByEnterDate
        assertEquals(sortedList[0].getDni(), res2[0]!!.getDni())

        for (repo in patientRepositories) {
            verify { repo.allOrderByEnterDate }
        }
    }

    @Test
    fun tesstGetAllOrderByName() {
        val sortedList: ArrayList<Patient> = ArrayList<Patient>()
        sortedList.add(Patient("12345678B", "Angel", "2000-01-01", "1000-01-01", Patient.TypePatient.NORMAL))
        sortedList.add(Patient("12345678A", "Bartolo", "2000-01-01", "2000-01-01", Patient.TypePatient.URGENT))

        for (repo in patientRepositories) {
            every { repo.allOrderByName } returns (sortedList)
        }

        patientController.setTypeRepo(PatientController.TypeRepo.LIST) // Tenemos que especificar el tipo de repositorio que entrará
        val res: List<Patient?> = patientController.allOrderByName
        assertEquals(sortedList[0].getDni(), res[0]!!.getDni())

        patientController.setTypeRepo(PatientController.TypeRepo.SET) // Tenemos que especificar el tipo de repositorio que entrará
        val res1: List<Patient?> = patientController.allOrderByName
        assertEquals(sortedList[0].getDni(), res1[0]!!.getDni())

        patientController.setTypeRepo(PatientController.TypeRepo.MAP) // Tenemos que especificar el tipo de repositorio que entrará
        val res2: List<Patient?> = patientController.allOrderByName
        assertEquals(sortedList[0].getDni(), res2[0]!!.getDni())
        for (repo in patientRepositories) {
            verify { repo.allOrderByName }
        }
    }

    @Test
    fun TestGetAllOrderByType() {
        val sortedList: ArrayList<Patient> = ArrayList<Patient>()
        sortedList.add(Patient("12345678B", "Angel", "2000-01-01", "1000-01-01", Patient.TypePatient.NORMAL))
        sortedList.add(Patient("12345678A", "Bartolo", "2000-01-01", "2000-01-01", Patient.TypePatient.URGENT))

        for (repo in patientRepositories) {
            every { repo.allOrderByType } returns (sortedList)
        }

        patientController.setTypeRepo(PatientController.TypeRepo.LIST) // Tenemos que especificar el tipo de repositorio que entrará
        val res: List<Patient?> = patientController.allOrderByType
        assertEquals(sortedList[0].getDni(), res[0]!!.getDni())

        patientController.setTypeRepo(PatientController.TypeRepo.SET) // Tenemos que especificar el tipo de repositorio que entrará
        val res1: List<Patient?> = patientController.allOrderByType
        assertEquals(sortedList[0].getDni(), res1[0]!!.getDni())

        patientController.setTypeRepo(PatientController.TypeRepo.MAP) // Tenemos que especificar el tipo de repositorio que entrará
        val res2: List<Patient?> = patientController.allOrderByType
        assertEquals(sortedList[0].getDni(), res2[0]!!.getDni())

        for (repo in patientRepositories) {
            verify { repo.allOrderByType }
        }
    }
}