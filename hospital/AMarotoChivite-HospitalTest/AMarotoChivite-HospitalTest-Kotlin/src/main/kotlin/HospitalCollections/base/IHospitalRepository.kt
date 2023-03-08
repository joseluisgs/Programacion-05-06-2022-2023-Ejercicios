package HospitalCollections.base

import HospitalCollections.models.Patient

interface IHospitalRepository : IGeneralRepository<Patient?, String?> {
    fun isFull(): Boolean

    val numPacients: Int

    val numByType: IntArray

    val allOrderByEnterDate: List<Patient?>

    val allOrderByName: List<Patient?>

    val allOrderByType: List<Patient?>
}