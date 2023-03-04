package HospitalCollectionsTest.repositories

import HospitalCollections.base.IHospitalRepository
import HospitalCollections.repositories.PatientSetRepository

class SetImplementacionRepositoryTest : RepositoryTest() {
    // Inicializa la implementación específica de la interfaz
    override var repoToEspecify: IHospitalRepository = PatientSetRepository()

    // Pruebas específicas si lo necesito...
    // Utilizo this.repoToEspecify el tipo de repo...
}