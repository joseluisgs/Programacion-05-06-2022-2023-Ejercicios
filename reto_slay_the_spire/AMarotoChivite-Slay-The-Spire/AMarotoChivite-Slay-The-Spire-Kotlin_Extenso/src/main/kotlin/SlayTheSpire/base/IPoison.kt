package SlayTheSpire.base

import SlayTheSpire.models.Characters.Character

interface IPoison {
    fun poison(char: Character): Int
}