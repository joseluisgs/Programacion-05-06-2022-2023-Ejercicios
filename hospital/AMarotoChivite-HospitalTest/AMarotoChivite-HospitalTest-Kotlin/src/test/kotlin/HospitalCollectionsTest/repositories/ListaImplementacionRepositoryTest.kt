package HospitalCollectionsTest.repositories

import HospitalCollections.base.IHospitalRepository
import HospitalCollections.repositories.PatientListRepository

class ListaImplementacionRepositoryTest : RepositoryTest() {
    // Inicializa la implementación específica de la interfaz
    override var repoToEspecify: IHospitalRepository = PatientListRepository()

    // Prueba específicas si lo necesito...
    // Utilizo this.repoToEspecify el tipo de repo...
}