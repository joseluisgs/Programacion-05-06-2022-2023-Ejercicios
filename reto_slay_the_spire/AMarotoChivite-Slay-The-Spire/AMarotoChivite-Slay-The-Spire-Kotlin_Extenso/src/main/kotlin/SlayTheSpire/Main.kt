package SlayTheSpire

import SlayTheSpire.models.Area.Map
import SlayTheSpire.models.Bag.RepoBag
import SlayTheSpire.models.Characters.PrincipalChar
import SlayTheSpire.models.Deck.RepoDeck

fun main() {
    // Genero el mapa
    val generalMap = Map(8)

    // Genero los repositorios del jugador
    val repoDeck = RepoDeck()
    val repoBag = RepoBag()

    // Genero el personaje Jugador
    val player = PrincipalChar(5, 3, repoDeck, repoBag, "Saber", 50)

    GameSlayOfSpire(generalMap, player, repoDeck, repoBag)
}
