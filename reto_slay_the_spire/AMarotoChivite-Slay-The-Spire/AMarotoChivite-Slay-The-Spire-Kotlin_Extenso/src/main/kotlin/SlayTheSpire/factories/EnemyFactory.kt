package SlayTheSpire.factories

import SlayTheSpire.models.Characters.Enemy

class EnemyFactory {
    companion object {
        fun create(): Enemy? {
            val probEnemy = (1..100).random()
            if (probEnemy <= 60) {
                val randomAttack = (3..5).random()
                val randomLife = (8..15).random()
                return Enemy.EnemyNormal(randomAttack, "Goblin", randomLife)
            }
            if (probEnemy in 61..80) {
                val randomAttack = (2..3).random()
                val randomLife = (6..10).random()
                val randomHeal = (5..7).random()
                return Enemy.EnemyHealer(randomHeal, randomAttack, "Curador", randomLife)
            }
            if (probEnemy in 81..100) {
                val randomAttack = (2..3).random()
                val randomLife = (6..8).random()
                val randomBuff = (2..3).random()
                return Enemy.EnemyBuffer(randomBuff, randomAttack, "Buffador", randomLife)
            }
            return null
        }
    }
}