package SlayTheSpire.models.Deck

import SlayTheSpire.models.Deck.Card.*
import java.util.*

class RepoDeck {

    private val deck: TreeMap<UUID, Card> = TreeMap()

    init {
        createEarlyCards()
    }


    private fun createEarlyCards() {
        // Creamos diez cartas

        // Tres de daño
        for (i in 1..3) {
            val card: CardDamage = CardDamage(5, "Tajo", 1)
            deck[card.getId()] = card
        }
        // Una de daño fuerte
        val cardDamage: CardDamage = CardDamage(10, "Puñalada", 2)
        deck[cardDamage.getId()] = cardDamage
        // Tres de curación
        for (i in 1..3) {
            val card: CardHeal = CardHeal(5, "Tirita", 1)
            deck[card.getId()] = card
        }
        // Una de curación fuerte
        val cardHeal: CardHeal = CardHeal(10, "Alivio", 2)
        deck[cardHeal.getId()] = cardHeal
        // Dos de veneno
        for (i in 1..2) {
            val card: CardPosion = CardPosion(1, "Veneno", 1)
            deck[card.getId()] = card
        }
    }

    fun show(): List<Card> {
        return deck.values.toList().sortedBy { it.name }
    }

    fun add(card: Card) {
        // repoDeck.put(card.getId(), card)
        deck[card.getId()] = card
    }

    fun delete(card: Card) {
        deck.remove(card.getId())
    }

    fun upgrade(selectedCard: Card, type: String): Boolean {
        when (selectedCard) {
            is CardDamage -> {
                if (type == "MANA") {
                    if (deck[selectedCard.getId()]!!.mana > 0) {
                        deck[selectedCard.getId()]!!.mana = deck[selectedCard.getId()]!!.mana - 1
                        return true
                    }
                } else if (type == "STAT") {
                    (deck[selectedCard.getId()] as CardDamage).damage =
                        (deck[selectedCard.getId()] as CardDamage).damage + 3
                    return true
                }
                println("El maná ya está en su máxima mejora!")
                return false
            }

            is CardHeal -> {
                if (type == "MANA") {
                    if (deck[selectedCard.getId()]!!.mana > 0) {
                        deck[selectedCard.getId()]!!.mana = deck[selectedCard.getId()]!!.mana - 1
                        return true
                    }
                } else if (type == "STAT") {
                    (deck[selectedCard.getId()] as CardHeal).heal =
                        (deck[selectedCard.getId()] as CardHeal).heal + 4
                    return true
                }
                println("El maná ya está en su máxima mejora!")
                return false
            }

            is CardPosion -> {
                if (type == "MANA") {
                    if (deck[selectedCard.getId()]!!.mana > 0) {
                        deck[selectedCard.getId()]!!.mana = deck[selectedCard.getId()]!!.mana - 1
                        return true
                    }
                } else if (type == "STAT") {
                    (deck[selectedCard.getId()] as CardPosion).posionDamage =
                        (deck[selectedCard.getId()] as CardPosion).posionDamage + 1
                    return true
                }
                println("El maná ya está en su máxima mejora!")
                return false
            }
        }
    }

    fun shuffle(): List<Card> {
        return deck.values.toList().shuffled()
    }
}