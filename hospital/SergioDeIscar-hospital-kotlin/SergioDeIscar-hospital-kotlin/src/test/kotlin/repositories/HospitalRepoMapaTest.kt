package repositories

class HospitalRepoMapaTest: HospitalRepoTest() {
    override fun createRepoEmpty() = HospitalRepoMapa()
    override fun createRepoShort() = HospitalRepoMapa(3)
}