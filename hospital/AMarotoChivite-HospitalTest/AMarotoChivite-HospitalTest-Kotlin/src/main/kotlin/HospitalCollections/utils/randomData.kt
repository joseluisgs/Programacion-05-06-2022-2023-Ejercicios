package HospitalCollections.utils

import java.util.*

object randomData {
    fun randomDni(): String {
        val LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val LETTERS_LENGTH = LETTERS.length
        val random = Random()
        val sb = StringBuilder()

        // Generamos 8 random dígitos
        for (i in 0..7) {
            sb.append(random.nextInt(10))
        }
        sb.append(LETTERS[random.nextInt(LETTERS_LENGTH)])
        return sb.toString()
    }
}