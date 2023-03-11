package SlayTheSpire.models.Characters

import SlayTheSpire.models.Bag.Item
import SlayTheSpire.models.Bag.Item.TypeItem
import SlayTheSpire.models.Bag.RepoBag
import SlayTheSpire.models.Deck.Card
import SlayTheSpire.models.Deck.RepoDeck

class PrincipalChar(
    var coins: Int,
    var mana: Int,
    private val repoDeck: RepoDeck,
    private val repoBag: RepoBag,
    name: String,
    life: Int
) : Character(
    name, life
) {
    //Inyección de dependencia de la bolsa y el mazo
    // Actúa como controlador el PrincipalChar

    // Funciones de la bolsa
    fun addItem(item: Item) {
        repoBag.add(item)
    }

    fun consumeItem(selectedItem: TypeItem) {
        if (selectedItem == TypeItem.HEAL) {
            repoBag.consume(selectedItem)
        }
        if (selectedItem == TypeItem.INMORTAL) {
            repoBag.consume(selectedItem)
        }
    }

    fun getBag(): List<Item> {
        return repoBag.show()
    }

    // Funciones mazo
    fun addCard(card: Card) {
        repoDeck.add(card)
    }

    fun deleteCard(card: Card) {
        repoDeck.delete(card)
    }

    fun shuffleDeck(): List<Card> {
        return repoDeck.shuffle()
    }

    fun getDeck(): List<Card> {
        return repoDeck.show()
    }

    fun upgradeCard(card: Card, selectedUpgrade: String): Boolean {
        return repoDeck.upgrade(card, selectedUpgrade)
    }

    override fun toString(): String {
        return "Estado Player -> (life=$life,coins=$coins, mana=$mana, inmortal=$inmortal, poisoned=$poisoned)"
    }

    fun printStatusAndHandAndBag(hand: List<Card>) {
        println("Estado Player -> (life=$life,coins=$coins, mana=$mana, inmortal=$inmortal, poisoned=$poisoned)")
        println("=== HAND ===")

        for (i in hand.indices) {
            println("${i + 1}: ${hand[i]}")
        }
        println("=== POCIONES ===")
        for (i in getBag().indices) {
            println("${i + 1}: ${getBag()[i]}")
        }
        println()
    }

    fun printAll() {
        println("Estado Player -> (life=$life,coins=$coins, mana=$mana, inmortal=$inmortal, poisoned=$poisoned)")
        println("=== DECK ===")
        for (i in getDeck().indices) {
            println("${i + 1}: ${getDeck()[i]}")
        }
        println("=== POCIONES ===")
        for (i in getBag().indices) {
            println("${i + 1}: ${getBag()[i]}")
        }
        println()
    }
}