package HospitalCollectionsTest.repositories

import HospitalCollections.base.IHospitalRepository
import HospitalCollections.repositories.PatientMapRepository

class MapImplementacionRepositoryTest : RepositoryTest() {
    // Inicializa la implementación específica de la interfaz
    override var repoToEspecify: IHospitalRepository = PatientMapRepository()

    // Pruebas específicas si lo necesito...
    // Utilizo this.repoToEspecify el tipo de repo...
}