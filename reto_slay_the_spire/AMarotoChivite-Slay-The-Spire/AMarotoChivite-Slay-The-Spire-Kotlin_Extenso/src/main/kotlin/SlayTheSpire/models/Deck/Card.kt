package SlayTheSpire.models.Deck

import java.util.*
import java.util.UUID.randomUUID

sealed class Card(var name: String, var mana: Int) {
    private var price: Int = 0
    private val id: UUID = randomUUID()
    fun getId(): UUID = id
    fun getPrice(): Int = price
    fun setPrice(price: Int) {
        this.price = price
    }

    abstract fun storeString(): String

    class CardDamage(var damage: Int, name: String, mana: Int) : Card(name, mana) {
        override fun toString(): String {
            return "(Name='$name',damage=$damage, mana=$mana)"
        }

        override fun storeString(): String {
            return "Precio: ${getPrice()} -> (Name='$name',damage=$damage, mana=$mana)"
        }
    }

    class CardHeal(var heal: Int, name: String, mana: Int) : Card(name, mana) {
        override fun toString(): String {
            return "(Name='$name',heal=$heal, mana=$mana)"
        }

        override fun storeString(): String {
            return "Precio: ${getPrice()} -> (Name='$name',heal=$heal, mana=$mana)"
        }
    }

    class CardPosion(var posionDamage: Int, name: String, mana: Int) : Card(name, mana) {
        override fun toString(): String {
            return "(Name='$name',posionDamage=$posionDamage, mana=$mana)"
        }

        override fun storeString(): String {
            return "Precio: ${getPrice()} -> (Name='$name',posionDamage=$posionDamage, mana=$mana)"
        }
    }
}