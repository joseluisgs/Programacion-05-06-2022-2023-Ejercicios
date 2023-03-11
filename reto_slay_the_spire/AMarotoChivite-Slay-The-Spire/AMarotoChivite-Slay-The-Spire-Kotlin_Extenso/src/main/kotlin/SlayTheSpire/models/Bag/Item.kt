package SlayTheSpire.models.Bag

import java.util.*
import java.util.UUID.randomUUID

class Item(val name: String, private val type: TypeItem) {
    private var price: Int = 0
    private val id: UUID = randomUUID()
    fun getId(): UUID = id
    fun getType(): TypeItem = type
    fun getPrice(): Int = price
    fun setPrice(price: Int) {
        this.price = price
    }

    override fun toString(): String {
        return "Poción: (Name='$name')"
    }

    fun storeString(): String {
        return "Precio: ${getPrice()} -> (Name='$name')"
    }

    enum class TypeItem {
        HEAL,
        INMORTAL
    }
}