package SlayTheSpire.factories

import SlayTheSpire.models.Bag.Item
import java.util.*

class BagFactory {
    companion object {
        private val bag: TreeMap<UUID, Item> = TreeMap()
        fun create(): MutableList<Item> {
            for (i in 1..3) {
                val randomItem = (1..2).random()
                if (randomItem == 1) {
                    val item = Item("Poción AntiVeneno", Item.TypeItem.HEAL)
                    val randomPrice = (12..18).random()
                    item.setPrice(randomPrice)
                    bag[item.getId()] = item
                }
                if (randomItem == 2) {
                    val item = Item("Poción Inmortal", Item.TypeItem.INMORTAL)
                    val randomPrice = (15..20).random()
                    item.setPrice(randomPrice)
                    bag[item.getId()] = item
                }
            }
            return bag.values.sortedBy { it.getPrice() }.toMutableList()
        }
    }
}