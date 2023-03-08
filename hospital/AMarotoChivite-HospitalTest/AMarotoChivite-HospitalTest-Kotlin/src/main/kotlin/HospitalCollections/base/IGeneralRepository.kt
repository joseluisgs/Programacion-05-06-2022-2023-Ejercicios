package HospitalCollections.base

interface IGeneralRepository<T, ID> {
    fun getAll(): List<T?>

    fun getById(id: ID): T?

    fun save(entity: T): T

    fun deleteById(id: ID): T?
}