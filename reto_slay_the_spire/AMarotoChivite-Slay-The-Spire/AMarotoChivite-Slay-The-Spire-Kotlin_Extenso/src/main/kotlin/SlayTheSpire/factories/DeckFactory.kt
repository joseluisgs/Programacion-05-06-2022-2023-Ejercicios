package SlayTheSpire.factories

import SlayTheSpire.models.Deck.Card
import SlayTheSpire.models.Deck.Card.*
import java.util.*

class DeckFactory {
    companion object {
        private val deck: TreeMap<UUID, Card> = TreeMap()

        fun create(): MutableList<Card> {
            for (i in 1..5) {
                val randomCard = (1..3).random()
                if (randomCard == 1) {
                    val randomDamage = (5..15).random()
                    val randomMana = (1..3).random()
                    val randomPrice = (5..10).random()

                    val card = CardDamage(randomDamage, "Pedrada", randomMana)
                    card.setPrice(randomPrice)
                    deck[card.getId()] = card
                }
                if (randomCard == 2) {
                    val randomHeal = (5..15).random()
                    val randomMana = (1..3).random()
                    val randomPrice = (5..10).random()

                    val card = CardHeal(randomHeal, "Cremita", randomMana)
                    card.setPrice(randomPrice)
                    deck[card.getId()] = card
                }
                if (randomCard == 3) {
                    val randomPosion = (2..5).random()
                    val randomMana = (1..3).random()
                    val randomPrice = (5..10).random()

                    val card = CardPosion(randomPosion, "Toxina", randomMana)
                    card.setPrice(randomPrice)
                    deck[card.getId()] = card
                }
            }
            return deck.values.sortedBy { it.getPrice() }.toMutableList()
        }
    }
}