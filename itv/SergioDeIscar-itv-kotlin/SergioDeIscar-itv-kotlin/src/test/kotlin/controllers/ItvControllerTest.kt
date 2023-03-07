package controllers

import exceptions.ItvMatriculaException
import interfaces.ItvExtension
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Coche
import models.Moto
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class ItvControllerTest {
    @MockK
    private lateinit var repository: ItvExtension

    @InjectMockKs
    private lateinit var controller: ItvController

    private val vehiculos = arrayOf(
        Coche("1234ABC", "Audi", LocalDate.now().minusDays(20),
            true, 160_000, 2),
        Coche("AAAAAAA", "Audi", LocalDate.now().minusDays(62),
            false, 10_000, 5), // Matricula ERROR
        Coche("1234ABE", "Volkswagen", LocalDate.now().minusYears(2),
            true, -1, 2), // Kilometro ERROR
        Coche("1234ABI", "Volkswagen", LocalDate.now().minusYears(2),
            true, -1, -1), // Puerta ERROR
        Moto("1234ABF", "Yamaha", LocalDate.now().minusYears(1),
            true, 170_000, 90), // Cilindrada ERROR
        Moto("1234ABG", "Honda", LocalDate.now().minusYears(3),
            true, 120_000, 125),
        Moto("1234ABH", "Honda", LocalDate.now().minusMonths(3),
            false, 93_000, 250),
        Coche("1234ABM", "Audi", LocalDate.now().minusDays(30),
            true, 130_000, 7)
    )

    @Test
    fun saveTest(){
        every { repository.save(vehiculos[0]) } returns vehiculos[0]

        assertAll(
            { assertThrows<ItvMatriculaException>{controller.save(vehiculos[1])} },
            { assertEquals(vehiculos[0], controller.save(vehiculos[0])) }
        )

        verify { repository.save(vehiculos[0]) }
    }

    @Test
    fun findTest(){
        every { repository.find("1234ABC") } returns vehiculos[0]

        assertAll(
            { assertThrows<ItvMatriculaException>{controller.find(vehiculos[1].matricula)} },
            { assertEquals(vehiculos[0], controller.find(vehiculos[0].matricula)) }
        )

        verify { repository.find("1234ABC") }
    }

    @Test
    fun deleteTest(){
        every { repository.delete("1234ABC") } returns vehiculos[0]

        assertAll(
            { assertThrows<ItvMatriculaException>{controller.delete(vehiculos[1].matricula)} },
            { assertEquals(vehiculos[0], controller.delete(vehiculos[0].matricula)) }
        )

        verify { repository.delete("1234ABC") }
    }
}