package SlayTheSpire.models.Characters

import java.util.*
import java.util.UUID.randomUUID

abstract class Character(
    val name: String,
    var life: Int,
) {
    private val id: UUID = randomUUID()
    var inmortal: Boolean = false
    var poisoned: Int = 0
}