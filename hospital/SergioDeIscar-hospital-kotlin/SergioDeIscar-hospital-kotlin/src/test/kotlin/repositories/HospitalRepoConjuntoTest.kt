package repositories

class HospitalRepoConjuntoTest: HospitalRepoTest() {
    override fun createRepoEmpty() = HospitalRepoConjunto()
    override fun createRepoShort() = HospitalRepoConjunto(3)
}