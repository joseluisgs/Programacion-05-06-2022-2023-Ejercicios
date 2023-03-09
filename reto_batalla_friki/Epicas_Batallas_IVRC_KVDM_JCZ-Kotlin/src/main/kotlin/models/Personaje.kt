package models

interface FullCounter{
    fun specialAction(): Int
}

interface Heal{
    fun specialAction(): Int?
}

interface Execute{
    fun specialAction(enemigo: Personaje): Int?
}

interface Gear3{
    fun specialAction(): Int
}

sealed class Personaje(var health: Int, val damage: Int){
    var isAlive: Boolean = true
    var mana: Int = 100
    abstract val manaCost: Int
    abstract val maxHealth: Int

    /**
     * Función que comprueba si el personaje está vivo o no, en ese último caso, muestra un mensaje
     */
    fun checkIfAlive(){
        if(health <= 0){
            health = 0
            isAlive = false
            println("$this, a muerto.")
        }
    }

    /**
     * Función que permite que los personajes reciban daño
     * @param damage es la cantidad de daño a recibir
     */
    open fun getHit(damage: Int){
        println("Se ha recibido un total de $damage de daño.")
        this.health -= damage
        checkIfAlive()

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Personaje

        if (health != other.health) return false
        if (damage != other.damage) return false
        if (isAlive != other.isAlive) return false
        if (mana != other.mana) return false
        if (manaCost != other.manaCost) return false
        if (maxHealth != other.maxHealth) return false

        return true
    }


    class Mordekai(health: Int, damage: Int): Personaje(health, damage), FullCounter {

        var damageReduction: Int = 0
        override val manaCost: Int = 50
        override val maxHealth: Int = health

        /**
         * Es la acción especial de mordekai, le permite reducir el daño recibido y pegar
         * @param damage es el daño que va a realizar el enemigo a Mordekai
         */
        override fun specialAction(): Int {
            if (this.mana >= manaCost){
                damageReduction = -4
                println("Se ha usado la habilidad especial de Mordekai, este turno recibiras $damageReduction puntos de daño, y también golpearas tu daño base + 3.")
                println("Realizar está habilidad a consumido un total de $manaCost mana.")
                this.mana -= manaCost
                println("Adicionalmente, este turno, Mordekai también golpea, con un total de ${this.damage+4} puntos de daño al enemigo.")
                println()
                return this.damage+4
            }else{
                println("No se ha podido realizar el ataque especial por lo que se recurrió a un ataque normal")
            }
            println()
            return damage

        }
        override fun getHit(damage: Int){
            val reducedDamage = damage + damageReduction
            if (reducedDamage < 0) {
                damageReduction = 0
            }
            println("Se ha recibido un total de $reducedDamage de daño, debido a que Mordekai ha bloqueado $damageReduction de daño.")
            this.health -= reducedDamage
            checkIfAlive()
            damageReduction = 0
        }


        override fun toString(): String {
            return "Mordekai(health=$health, damage=$damage, isAlive=$isAlive, mana=$mana)"
        }
    }

    class Rigby(health: Int, damage: Int): Personaje(health, damage), Heal{

        override val maxHealth = health

        override val manaCost: Int = 40

        /**
         * Acción especial de rigby que le cura como mucho 7 puntos de vida.
         */
        override fun specialAction(): Int {
            if (this.mana >= manaCost){
                println("Se ha usado la habilidad especial de Rigby, por lo que se va a recuperar la vida 7 puntos, hasta un maximo de $maxHealth.")
                println("Realizar está habilidad a consumido un total de $manaCost mana.")
                this.mana -= manaCost
                var cura = 7
                if ((maxHealth - this.health) < 7) cura -= (maxHealth - this.health)
                println("Como la vida actualmente es, ${this.health}, recuperara $cura puntos de vida.")
                this.health += cura
                println()
                return 0
            }else{
                println("No se ha podido realizar el ataque especial por lo que se recurrió a un ataque normal")
            }
            return damage
        }
        override fun toString(): String {
            return "Rigby(health=$health, damage=$damage, isAlive=$isAlive, mana=$mana)"
        }
    }

    class Finn(health: Int, damage: Int): Personaje(health, damage), Execute{

        override val manaCost: Int = 100
        override val maxHealth: Int = health

        /**
         * Occión especial de Finn que le permite ejecutar a su enemigo si su vida está al 50% o por debajo
         * @param enemigo es el enemigo a ejecutar, si es posible
         *
         */
        override fun specialAction(enemigo: Personaje): Int {
            if (this.mana >= manaCost){
                println("Se ha usado la habilidad especial de Finn, ahora Finn va a ejecutar a: $enemigo")
                if ((enemigo.maxHealth / 2) >= enemigo.health) {
                    enemigo.isAlive = false
                    println("Realizar está habilidad a consumido un total de $manaCost mana.")
                    this.mana -= manaCost
                    println()
                } else {
                    println("Sin embargo, el enemigo no tenía la vida suficiente como para ejecutarlo, por lo que se va a realizar un ataque normal de $damage puntos de daño.")
                    println("Como no ha realizado la habilidad, no se le a gastado mana.")
                    println()
                    return damage
                }
                return 0
            }else{
                println("No se ha podido realizar el ataque especial por lo que se recurrió a un ataque normal")
            }
            return damage
        }


        override fun toString(): String {
            return "Finn(health=$health, damage=$damage, isAlive=$isAlive, mana=$mana)"
        }
    }

    class Jake(health: Int, damage: Int): Personaje(health, damage), Gear3{

        val specialDamage = 8
        override val maxHealth: Int = health
        override val manaCost: Int = 50

        /**
         * acción especial de Jake que le permite pegar cual camión
         * @return el daño de camión que realizará Jake
         */
        override fun specialAction(): Int {
            if (this.mana >= manaCost){
                println("Se ha usado la habilidad especial de Jake, ahora Jake realizará un ataque de $specialDamage puntos de daño.")
                println("Realizar está habilidad a consumido un total de $manaCost mana.")
                this.mana -= manaCost
                println()
                return specialDamage
            }else{
                println("No se ha podido realizar el ataque especial por lo que se recurrió a un ataque normal")
            }
            return damage
        }


        override fun toString(): String {
            return "Jake(health=$health, damage=$damage, isAlive=$isAlive, mana=$mana)"
        }
    }
}