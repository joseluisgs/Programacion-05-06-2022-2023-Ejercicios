package SlayTheSpire.models.Characters

import SlayTheSpire.base.IEnemyBuffer
import SlayTheSpire.base.IHeal
import SlayTheSpire.base.IPoison

sealed class Enemy(
    var basicDamage: Int,
    name: String,
    life: Int
) : Character(
    name, life
) {
    class EnemyNormal(
        basicDamage: Int,
        name: String,
        life: Int
    ) : Enemy(
        basicDamage,
        name,
        life
    ) {
        override fun toString(): String {
            return "EnemyNormal(life=$life, basicDamage=$basicDamage,poisoned=$poisoned)"
        }
    }

    class EnemyHealer(
        private val healing: Int,
        basicDamage: Int,
        name: String,
        life: Int
    ) : Enemy(
        basicDamage,
        name,
        life
    ), IHeal {
        override fun heal(): Int {
            return healing
        }

        override fun toString(): String {
            return "EnemyHealer(life=$life, basicDamage=$basicDamage,healing=$healing, poisoned=$poisoned)"
        }
    }

    class EnemyBuffer(
        private val buffing: Int,
        basicDamage: Int,
        name: String,
        life: Int
    ) : Enemy(
        basicDamage,
        name,
        life
    ), IEnemyBuffer {
        override fun buff(): Int {
            return buffing
        }

        override fun toString(): String {
            return "EnemyBuffer(life=$life, basicDamage=$basicDamage,buff=$buffing, poisoned=$poisoned)"
        }
    }

    class Boss(
        private val healing: Int,
        private val buffing: Int,
        private val applyPosion: Int,
        basicDamage: Int,
        name: String,
        life: Int
    ) : Enemy(
        basicDamage,
        name,
        life
    ), IEnemyBuffer, IHeal, IPoison {
        override fun buff(): Int {
            return buffing
        }

        override fun heal(): Int {
            return healing
        }

        override fun poison(char: Character): Int {
            char.poisoned = char.poisoned + applyPosion
            return applyPosion
        }

        override fun toString(): String {
            return "Boss(life=$life, basicDamage=$basicDamage,healing=$healing, buffing=$buffing,aplyPosicon=$applyPosion poisoned=$poisoned)"
        }
    }
}