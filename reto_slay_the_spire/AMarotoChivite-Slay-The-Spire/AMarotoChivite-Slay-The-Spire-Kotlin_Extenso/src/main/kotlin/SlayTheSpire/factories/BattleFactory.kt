package SlayTheSpire.factories

import SlayTheSpire.models.Characters.Enemy

class BattleFactory {
    companion object {
        fun create(): MutableList<Enemy> {
            val storeEnemy = ArrayList<Enemy>()

            val random = (1..100).random()
            if (random <= 45) {
                storeEnemy.add(EnemyFactory.create()!!)
            }
            if (random in 46..90) {
                storeEnemy.add(EnemyFactory.create()!!)
                storeEnemy.add(EnemyFactory.create()!!)
            }
            if (random > 90) {
                storeEnemy.add(EnemyFactory.create()!!)
                storeEnemy.add(EnemyFactory.create()!!)
                storeEnemy.add(EnemyFactory.create()!!)
            }
            return storeEnemy.toMutableList()
        }
    }
}