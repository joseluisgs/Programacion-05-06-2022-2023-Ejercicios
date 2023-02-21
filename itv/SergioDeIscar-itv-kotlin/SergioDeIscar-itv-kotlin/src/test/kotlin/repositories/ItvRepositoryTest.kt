package repositories

class ItvRepositoryTest: ItvRepoGeneric() {
    override fun createRepoEmpty() = ItvRepository()
    override fun createRepoShort() = ItvRepository(3)
}