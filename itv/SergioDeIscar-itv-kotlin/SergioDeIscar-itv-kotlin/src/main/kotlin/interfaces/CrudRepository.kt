package interfaces

interface CrudRepository<T, ID> {
    fun save(t: T): T?
    fun find(id: ID): T?
    fun delete(id: ID): T?
    fun getAll(): List<T>
    fun saveAll(list: List<T>)
}