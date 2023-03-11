package SlayTheSpire.models.Deck

import SlayTheSpire.base.IQueue

class Queue<Card> : IQueue<Card> {
    private val items: MutableList<Card> = mutableListOf()
    override fun enqueue(item: Card) {
        items.add(item)
    }

    override fun dequeue(): Card? {
        return items.removeFirstOrNull()
    }

    override fun first(): Card? {
        return items.firstOrNull()
    }

    override fun size(): Int {
        return items.size
    }

    override fun isEmpty(): Boolean {
        return items.isEmpty()
    }

    fun getAll(): MutableList<Card> {
        return items
    }
}