import controllers.HospitalController
import repositories.HospitalListaRepository

fun main() {
    val repo = HospitalListaRepository()
    val controller = HospitalController(repo)

    println(controller.estaCompleto())
}