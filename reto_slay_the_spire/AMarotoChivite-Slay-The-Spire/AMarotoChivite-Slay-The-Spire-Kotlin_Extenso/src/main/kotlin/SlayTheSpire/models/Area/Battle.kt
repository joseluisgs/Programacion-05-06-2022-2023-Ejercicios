package SlayTheSpire.models.Area

import SlayTheSpire.factories.BattleFactory
import SlayTheSpire.filters.checkSelection
import SlayTheSpire.models.Bag.Item
import SlayTheSpire.models.Characters.Enemy
import SlayTheSpire.models.Characters.PrincipalChar
import SlayTheSpire.models.Deck.Card
import SlayTheSpire.models.Deck.Queue
import kotlin.system.exitProcess

class Battle {
    companion object {
        fun instanceBattle(player: PrincipalChar, typeBattle: Int) {
            // Barajamos toda la baraja
            val deckShuffled = player.shuffleDeck().toMutableList()

            // Instanciamos la cola para crear una baraja que rote
            val deckEnqueue = Queue<Card>()
            for (i in 0 until deckShuffled.size) {
                deckEnqueue.enqueue(deckShuffled[i])
            }

            // Instanciamos una mano con las 4 primeras cartas de la cola-mazo
            val hand = mutableListOf<Card>()
            for (i in 1 until 5) {
                // Agregamos la primera carta de la cola a la mano
                hand.add(deckEnqueue.first()!!)
                // Eliminamos la primera carta de la cola del mazo encolado
                deckEnqueue.dequeue()
            }

            // Batalla estándar, typeBattle = 1 
            // Empezamos la batalla
            if (typeBattle == 1) {
                initBattle(player, hand, deckEnqueue)
            }

            // Batalla contra el Jefe, typeBattle = 2
            // Empezamos la batalla
            if (typeBattle == 2) {
                initBoss(player, hand, deckEnqueue)
            }
        }

        private fun initBoss(player: PrincipalChar, hand: MutableList<Card>, deckEnqueue: Queue<Card>) {
            var countRound: Int = 0

            // Instanciamos la batalla
            val boss: Enemy.Boss = Enemy.Boss(6, 4, 2, 2, "Chamán Místico", 250)

            while (true) {
                // Ordenamos la mano siempre en cada vuelta
                val sortHand = hand.sortedBy { it.name }

                // Vemos si ha ganado el jugador, para la vuelta de la ronda
                if (checkWinBattleBoss(boss)) {
                    println("===================")
                    println("HAS GANADO EL JUEGO!")
                    println("===================")
                    exitProcess(1)
                }

                // Inicia la acción del player
                println("==== RONDA ${countRound + 1} ====")
                showAllStatusBattleBoss(player, boss, sortHand, deckEnqueue)

                println("Qué carta quieres utilizar? Elige por el número...")
                println("Si quieres utilizar un poción, escribe: P")
                println("Si quieres terminar el turno, escribe: X")

                val decisionCardOrPotionOrFinal: String = readln()

                if (decisionCardOrPotionOrFinal == "P") {
                    // Usar pociones
                    useBag(player)
                } else if (decisionCardOrPotionOrFinal == "X") {
                    // Para terminar el turno y que actúe el jefe
                    actionsOnFinalPlayerTurn(player, hand, deckEnqueue)

                    // Acciones de enemigos
                    bossActions(player, boss)
                    checkGameOver(player)
                    restartStatsAfterBossAction(player, boss)

                    countRound++
                } else if (checkSelection(decisionCardOrPotionOrFinal, hand.size)) {
                    val card = sortHand[decisionCardOrPotionOrFinal.toInt() - 1]
                    if (card.mana > player.mana) {
                        println("No tienes suficiente maná para activar esa carta...")
                        Thread.sleep(1000)
                    } else {
                        // Si superamos el filtro se activa la carta
                        // Eliminamos la carta de la mano, la introducimos al final del mazo y restamos el maná que nos costó

                        if (card is Card.CardHeal) {
                            println("Te curas ${card.heal} de vida")
                            player.life = player.life + card.heal
                            player.mana = player.mana - card.mana
                            deckEnqueue.enqueue(card)
                            hand.remove(card)
                        }
                        if (card is Card.CardDamage) {
                            println("Has hecho ${card.damage} de daño al jefe")
                            boss.life = boss.life - card.damage
                            player.mana = player.mana - card.mana
                            deckEnqueue.enqueue(card)
                            hand.remove(card)
                        }
                        if (card is Card.CardPosion) {
                            println("Has aplicado ${card.posionDamage} de veneno al jefe")
                            player.mana = player.mana - card.mana
                            boss.poisoned = boss.poisoned + card.posionDamage
                            deckEnqueue.enqueue(card)
                            hand.remove(card)
                        }
                    }
                }
            }
        }

        private fun restartStatsAfterBossAction(player: PrincipalChar, boss: Enemy.Boss) {
            // Acciones necesarias para el siguiente turno y acciones últimas de los enemigos
            // Mana player
            player.mana = 3
            // Inmortal player
            player.inmortal = false
            // Aplicar Veneno boss
            boss.life = boss.life - boss.poisoned
        }

        private fun bossActions(player: PrincipalChar, boss: Enemy.Boss) {
            val actionRandomBoss = (1..4).random()
            // 1 = atacar
            // 2 = buffar
            // 3 = curar
            // 4 = envenenar

            if (actionRandomBoss == 1 && boss.life > 0) {
                if (!player.inmortal) {
                    player.life = player.life - boss.basicDamage
                    println("Has recibido ${boss.basicDamage} del jefe")
                } else {
                    println("El jugador es inmortal y no recibe los ${boss.basicDamage} daños del jefe")
                }
            }
            if (actionRandomBoss == 2 && boss.life > 0) {
                boss.basicDamage = boss.basicDamage + boss.buff()
                println("El jefe se ha potenciado ${boss.buff()} así mismo")
            }
            if (actionRandomBoss == 3 && boss.life > 0) {
                boss.life = boss.life + boss.heal()
                println("El jefe se ha curado ${boss.heal()} así mismo")
            }
            if (actionRandomBoss == 4 && boss.life > 0) {
                println("El jefe te ha envenenado ${boss.poison(player)} de veneno")
            }
            println()
        }

        private fun showAllStatusBattleBoss(
            player: PrincipalChar,
            boss: Enemy.Boss,
            sortHand: List<Card>,
            deckEnqueue: Queue<Card>
        ) {
            println("=== JEFE ===")
            println(boss)

            println("=== TU ESTADO ===")
            player.printStatusAndHandAndBag(sortHand)

            println("Cartas en el mazo: ${deckEnqueue.size()}")
        }

        /**
         * Comprobamos si ha muerto el jefe
         */
        private fun checkWinBattleBoss(boss: Enemy.Boss): Boolean {
            if (boss.life == 0) {
                return true
            }
            return false
        }

        private fun initBattle(
            player: PrincipalChar,
            hand: MutableList<Card>,
            deckEnqueue: Queue<Card>
        ) {
            var countRound: Int = 0
            var countTotalDamageToCalculateCoins: Int = 0

            // Instanciamos la batalla
            var battle = BattleFactory.create()

            while (true) {
                // Ordenamos la mano siempre en cada vuelta
                val sortHand = hand.sortedBy { it.name }

                // Vemos si ha ganado el jugador, para la vuelta de la ronda
                if (checkWinBattle(battle, countTotalDamageToCalculateCoins)) {
                    // Mana player
                    player.mana = 3

                    // Inmortal player
                    player.inmortal = false

                    // Damos la opción de eliminar una carta
                    wantDeleteCard(player)

                    // Asignar monedas al personaje al terminar la batalla
                    player.coins = player.coins + (countTotalDamageToCalculateCoins / 6) + 5
                    break
                }

                // Inicia la acción del player
                println("==== RONDA ${countRound + 1} ====")
                showAllStatusBattle(player, battle, sortHand, deckEnqueue)

                println("Qué carta quieres utilizar? Elige por el número...")
                println("Si quieres utilizar un poción, escribe: P")
                println("Si quieres terminar el turno, escribe: X")

                val decisionCardOrPotionOrFinal: String = readln()

                if (decisionCardOrPotionOrFinal == "P") {
                    // Usar pociones
                    useBag(player)
                } else if (decisionCardOrPotionOrFinal == "X") {
                    // Para terminar el turno y que actúen los enemigos
                    actionsOnFinalPlayerTurn(player, hand, deckEnqueue)

                    // Acciones de enemigos
                    countTotalDamageToCalculateCoins =
                        enemyActions(player, battle, countTotalDamageToCalculateCoins)
                    checkGameOver(player)
                    restartStatsAfterEnemiesAction(player, battle)

                    countRound++
                } else if (checkSelection(decisionCardOrPotionOrFinal, hand.size)) {
                    val card = sortHand[decisionCardOrPotionOrFinal.toInt() - 1]
                    if (card.mana > player.mana) {
                        println("No tienes suficiente maná para activar esa carta...")
                        Thread.sleep(1000)
                    } else {
                        // Si superamos el filtro se activa la carta
                        // Eliminamos la carta de la mano, la introducimos al final del mazo y restamos el maná que nos costó

                        if (card is Card.CardHeal) {
                            player.life = player.life + card.heal
                            player.mana = player.mana - card.mana
                            deckEnqueue.enqueue(card)
                            hand.remove(card)
                        }

                        if (card is Card.CardDamage) {
                            println("A qué enemigo quieres atacar? Elige por el número...")
                            println("Si quieres salir, escribe:  X")

                            val decisionEnemy: String = readln()

                            if (checkSelection(decisionEnemy, battle.size)) {
                                val enemy = battle[decisionEnemy.toInt() - 1]
                                enemy.life = enemy.life - card.damage
                                player.mana = player.mana - card.mana
                                deckEnqueue.enqueue(card)
                                hand.remove(card)
                            }
                            if (decisionEnemy == "X") {
                                break
                            }
                        }

                        if (card is Card.CardPosion) {
                            println("A qué enemigo quieres atacar? Elige por el número...")
                            println("Si quieres salir, escribe:  X")

                            val decisionEnemy: String = readln()

                            if (checkSelection(decisionEnemy, battle.size)) {
                                val enemy = battle[decisionEnemy.toInt() - 1]
                                player.mana = player.mana - card.mana
                                enemy.poisoned = enemy.poisoned + card.posionDamage
                                deckEnqueue.enqueue(card)
                                hand.remove(card)
                            }

                            if (decisionEnemy == "X") {
                                break
                            }
                        }
                    }
                }
                // Limpiamos los enemigos con 0 de vida o menos

                // Podríamos con programación funcional, buscando aquellos personajes que tengan cero de vida
                //battle = battle.filter { it.life > 0 }.toMutableList()

                // La manera tradicional creando una lista temporal para agregar los enemigos que solo estén vivos
                val newBattle = mutableListOf<Enemy>()
                for (enemy in battle) {
                    if (enemy.life > 0) {
                        newBattle.add(enemy)
                    }
                }
                battle = newBattle
            }
        }

        private fun wantDeleteCard(player: PrincipalChar) {
            while (true) {
                println("Deseas eliminar una carta de tu mazo? (Y/N):")
                val decision: String = readln()

                if (decision.uppercase() == "N") {
                    break
                }
                if (decision.uppercase() == "Y") {
                    decisionWhichCardDelete(player)
                    break
                }
            }
        }

        private fun decisionWhichCardDelete(player: PrincipalChar) {
            while (true) {
                println("=== ELIMINACIÓN DE UNA CARTA ===")
                for (i in player.getDeck().indices) {
                    println("${i + 1}: ${player.getDeck()[i]}")
                }
                println("Elige por número...")
                println("Si quieres salir, escribe:  X")

                val decision: String = readln()
                if (checkSelection(decision, player.getDeck().size)) {
                    player.deleteCard(player.getDeck().toMutableList()[decision.toInt() - 1])
                    break
                }
                if (decision == "X") {
                    break
                }
            }
        }

        private fun actionsOnFinalPlayerTurn(player: PrincipalChar, hand: MutableList<Card>, deckEnqueue: Queue<Card>) {
            // Acciones finales del turno de player
            // Aplicar Veneno player
            if (!player.inmortal) {
                player.life = player.life - player.poisoned
            }
            // Dar 2 cartas nuevas del mazo a la mano
            for (i in 1 until 3) {
                if (deckEnqueue.size() > 0) {
                    // Agregamos la primera carta de la cola a la mano
                    hand.add(deckEnqueue.first()!!)
                    // Eliminamos la primera carta de la cola del mazo encolado
                    deckEnqueue.dequeue()
                }
            }
        }

        private fun checkGameOver(player: PrincipalChar) {
            // Checkeamos si hemos muerto después de la acción de los enemigos
            if (player.life <= 0) {
                println("HAS PERDIDO!")
                exitProcess(0)
            }
        }

        private fun restartStatsAfterEnemiesAction(player: PrincipalChar, battle: MutableList<Enemy>) {
            // Acciones necesarias para el siguiente turno y acciones últimas de los enemigos
            // Mana player
            player.mana = 3
            // Inmortal player
            player.inmortal = false
            // Aplicar Veneno enemigos
            for (i in 0 until battle.size) {
                battle[i].life = battle[i].life - battle[i].poisoned
            }
        }

        private fun useBag(player: PrincipalChar) {
            while (true) {
                println("Elige la poción, por número....")
                println("Si quieres salir, escribe:  X")

                val potionPosition: String = readln()
                if (checkSelection(potionPosition, player.getBag().size)) {
                    val potion = player.getBag()[potionPosition.toInt() - 1]
                    if (potion.getType() == Item.TypeItem.HEAL) {
                        player.life = player.life + 20
                        player.poisoned = 0
                        player.consumeItem(potion.getType())
                        break
                    }
                    if (potion.getType() == Item.TypeItem.INMORTAL) {
                        player.inmortal = true
                        player.consumeItem(potion.getType())
                        break
                    }
                }
                if (potionPosition == "X") {
                    break
                }
            }
        }

        private fun enemyActions(
            player: PrincipalChar,
            battle: MutableList<Enemy>,
            countTotalDamageToCalculateCoins: Int
        ): Int {
            var newCountDamage: Int = countTotalDamageToCalculateCoins

            // Acciones enemigos aleatorias
            for (i in 0 until battle.size) {
                if (battle[i] is Enemy.EnemyNormal && battle[i].life > 0) {
                    if (!player.inmortal) {
                        player.life = player.life - battle[i].basicDamage
                        newCountDamage += battle[i].basicDamage
                        println("Has recibido ${battle[i].basicDamage} del enemigo normal")
                    } else {
                        println("El jugador es inmortal y no recibe los ${battle[i].basicDamage} daños del enemigo normal")
                    }
                }
                if (battle[i] is Enemy.EnemyHealer && battle[i].life > 0) {
                    val probAction = (1..100).random()
                    if (probAction <= 75) {
                        val probTarget = (1..2).random()
                        if (probTarget == 1) {
                            battle[i].life = battle[i].life + (battle[i] as Enemy.EnemyHealer).heal()
                            println("El curador se ha curado ${(battle[i] as Enemy.EnemyHealer).heal()} así mismo")
                        }
                        if (probTarget == 2) {
                            for (j in 0 until battle.size) {
                                if (battle[j] != battle[i]) {
                                    battle[j].life = battle[j].life + (battle[i] as Enemy.EnemyHealer).heal()
                                    println("El curador ha curado ${(battle[i] as Enemy.EnemyHealer).heal()}")
                                }
                                if (battle.size == 1) {
                                    battle[i].life = battle[i].life + (battle[i] as Enemy.EnemyHealer).heal()
                                    println("El curador se ha curado ${(battle[i] as Enemy.EnemyHealer).heal()} así mismo")
                                }
                            }
                        }
                    } else {
                        if (!player.inmortal) {
                            player.life = player.life - battle[i].basicDamage
                            newCountDamage += battle[i].basicDamage
                            println("Has recibido ${battle[i].basicDamage} del curador")
                        } else {
                            println("El jugador es inmortal y no recibe los ${battle[i].basicDamage} daños del curador")
                        }
                    }
                }
                if (battle[i] is Enemy.EnemyBuffer && battle[i].life > 0) {
                    val probAction = (1..100).random()
                    if (probAction <= 75) {
                        val probTarget = (1..2).random()
                        if (probTarget == 1) {
                            battle[i].basicDamage = battle[i].basicDamage + (battle[i] as Enemy.EnemyBuffer).buff()
                            println("El potenciador se ha potenciado ${(battle[i] as Enemy.EnemyBuffer).buff()} así mismo")
                        }
                        if (probTarget == 2) {
                            for (j in 0 until battle.size) {
                                if (battle[j] != battle[i]) {
                                    battle[j].basicDamage =
                                        battle[j].basicDamage + (battle[i] as Enemy.EnemyBuffer).buff()
                                    println("El potenciador ha potenciado ${(battle[i] as Enemy.EnemyBuffer).buff()}")
                                }
                                if (battle.size == 1) {
                                    battle[i].basicDamage =
                                        battle[i].basicDamage + (battle[i] as Enemy.EnemyBuffer).buff()
                                    println("El potenciador se ha potenciado ${(battle[i] as Enemy.EnemyBuffer).buff()} así mismo")
                                }
                            }
                        }
                    } else {
                        if (!player.inmortal) {
                            player.life = player.life - battle[i].basicDamage
                            newCountDamage += battle[i].basicDamage
                            println("Has recibido ${battle[i].basicDamage} del potenciador")
                        } else {
                            println("El jugador es inmortal y no recibe los ${battle[i].basicDamage} daños del potenciador")
                        }
                    }
                }
            }
            println()
            return newCountDamage
        }

        private fun checkWinBattle(battle: MutableList<Enemy>, countTotalDamageToCalculateCoins: Int): Boolean {
            // Checkeamos si han muerto los enemigos después de nuestras acciones
            if (battle.size == 0) {
                println("BATALLA GANADA!")
                println("Recibes: ${(countTotalDamageToCalculateCoins / 6) + 5} monedas")
                return true
            }
            return false
        }

        private fun showAllStatusBattle(
            player: PrincipalChar,
            battle: MutableList<Enemy>,
            hand: List<Card>,
            deckEnqueue: Queue<Card>
        ) {
            println("=== ENEMIGO/s ===")
            for (i in battle.indices) {
                println("${i + 1}: ${battle[i]}")
            }

            println("=== TU ESTADO ===")
            player.printStatusAndHandAndBag(hand)

            println("Cartas en el mazo: ${deckEnqueue.size()}")

            // Solo para visualizar el mazo que tenemos oculto
            //    println("=== TU ESTADO BARAJA===")
            //    for (i in deckEnqueue.getAll().indices) {
            //        println("${i + 1}: ${deckEnqueue.getAll()[i]}")
            //    }
        }

    }
}