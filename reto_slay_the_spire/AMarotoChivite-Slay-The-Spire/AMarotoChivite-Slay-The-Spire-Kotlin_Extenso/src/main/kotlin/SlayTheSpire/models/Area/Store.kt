package SlayTheSpire.models.Area

import SlayTheSpire.factories.BagFactory
import SlayTheSpire.factories.DeckFactory
import SlayTheSpire.filters.checkSelection
import SlayTheSpire.models.Bag.Item
import SlayTheSpire.models.Characters.PrincipalChar
import SlayTheSpire.models.Deck.Card

class Store(private val deck: List<Card>?, private val bag: List<Item>?) {
    companion object {
        fun initStore(player: PrincipalChar) {
            // Genera tienda aleatoria
            val randomDeck: MutableList<Card> = DeckFactory.create()
            val randomBag: MutableList<Item> = BagFactory.create()

            do {
                println("=== TIENDA CARTAS ===")
                var count: Int = 0
                for (i in randomDeck.indices) {
                    println("${count + 1}: ${randomDeck[i].storeString()}")
                    count++
                }
                println("=== TIENDA POCIONES ===")
                for (i in randomBag.indices) {
                    println("${count + 1}: ${randomBag[i].storeString()}")
                    count++
                }
                println()

                println("Tienes ${player.coins} monedas")
                println("Que deseas comprar? Selecciona mínimo 1 y máximo $count")
                val decision: String = readln()

                if (checkSelection(decision, count)) {
                    var countToGet: Int = 1
                    for (i in randomDeck.indices) {
                        if (countToGet == decision.toInt()) {
                            if (randomDeck[i].getPrice() <= player.coins) {
                                player.coins = player.coins - randomDeck[i].getPrice()
                                player.addCard(randomDeck[i])
                                randomDeck.apply {
                                    removeAt(i)
                                }

                                println("CARTA COMPRADA...")
                            } else {
                                println("No tienes las monedas suficientes, tienes: ${player.coins}")
                            }
                        }
                        countToGet++
                    }
                    for (i in randomBag.indices) {
                        if (countToGet == decision.toInt()) {
                            if (randomBag[i].getPrice() <= player.coins) {
                                player.coins = player.coins - randomBag[i].getPrice()
                                player.addItem(randomBag[i])
                                randomBag.apply {
                                    removeAt(i)
                                }
                                println("POCIÓN COMPRADA...")
                            } else {
                                println("No tienes las monedas suficientes, tienes: ${player.coins}")
                            }
                        }
                        countToGet++
                    }
                }
            } while (!exit("Deseas seguir comprando? (Y/N):"))
        }

        private fun exit(message: String): Boolean {
            println(message)
            val decision: String = readln()

            if (decision.uppercase() == "N") {
                return true
            }
            return false
        }
    }
}
