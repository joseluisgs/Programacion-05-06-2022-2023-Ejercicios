package controllers

import enums.TipoPaciente
import exceptions.DniNotValidException
import interfaces.HospitalExtension
import models.Paciente
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals
import kotlin.test.assertNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class HospitalControllerTest {
    @MockK
    private lateinit var repository: HospitalExtension

    @InjectMockKs
    private lateinit var controller: HospitalController

    private val pacientes = arrayOf(
        Paciente("30609298R", "Ztest", tipo = TipoPaciente.NORMAL),
        Paciente("42298425E", "Atest", tipo = TipoPaciente.NORMAL),
        Paciente("00972994W", "Etest", tipo = TipoPaciente.NORMAL),

        Paciente("07932195R", "Ttest", tipo = TipoPaciente.URGENCIA),
        Paciente("AAAAAAAAA", "Itest", tipo = TipoPaciente.URGENCIA), // DNI ERROR
    )

    @Test
    fun saveTest(){
        every { repository.estaCompleto() } returns false
        every { repository.save(pacientes[0]) } returns pacientes[0]

        assertAll(
            { assertThrows<DniNotValidException>{controller.save(pacientes[4])} },
            { assertEquals(pacientes[0], controller.save(pacientes[0])) }
        )

        verify { repository.estaCompleto() }
        verify { repository.save(pacientes[0]) }
    }

    @Test
    fun findTest(){
        every { repository.find("30609298R") } returns pacientes[0]
        every { repository.find("42298425E") } returns null

        assertAll(
            { assertThrows<DniNotValidException>{controller.find(pacientes[4].dni)} },
            { assertEquals(pacientes[0], controller.find(pacientes[0].dni)) },
            { assertNull(controller.find(pacientes[1].dni)) }
        )

        verify { repository.find("30609298R") }
        verify { repository.find("42298425E") }
    }

    @Test
    fun deleteTest(){
        every { repository.delete("30609298R") } returns pacientes[0]
        every { repository.delete("42298425E") } returns null

        assertAll(
            { assertThrows<DniNotValidException>{controller.delete(pacientes[4].dni)} },
            { assertEquals(pacientes[0], controller.delete(pacientes[0].dni)) },
            { assertNull(controller.delete(pacientes[1].dni)) }
        )

        verify { repository.delete("30609298R") }
        verify { repository.delete("42298425E") }
    }
}