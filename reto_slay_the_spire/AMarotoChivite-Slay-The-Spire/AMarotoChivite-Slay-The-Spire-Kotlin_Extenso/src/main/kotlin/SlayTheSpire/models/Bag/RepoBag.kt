package SlayTheSpire.models.Bag

import SlayTheSpire.models.Bag.Item.TypeItem
import java.util.*

class RepoBag {
    private val bag: TreeMap<UUID, Item> = TreeMap()

    fun show(): List<Item> {
        return bag.values.toList()
    }

    fun add(item: Item) {
        bag[item.getId()] = item
    }

    fun consume(selectedItem: TypeItem): TypeItem? {
        val listBag = bag.values.toList()
        val item: Item = listBag.find { it.getType() == selectedItem } ?: return null

        return when (item.getType()) {
            TypeItem.HEAL -> {
                val deletedTypeItem = item.getType()
                bag.remove(item.getId())
                deletedTypeItem
            }

            TypeItem.INMORTAL -> {
                val deletedTypeItem = item.getType()
                bag.remove(item.getId())
                deletedTypeItem
            }
        }
    }
}