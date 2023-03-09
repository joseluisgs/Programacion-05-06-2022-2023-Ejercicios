package models

class TableroJuego(val tamañoFilasColumnas: Int) {

    var field: Array<Array<Personaje?>> = Array(tamañoFilasColumnas) { Array(tamañoFilasColumnas) { null } }
    val t1: Team = Team(Personaje.Mordekai(30, 3), Personaje.Rigby(30, 3))
    val t2: Team = Team(Personaje.Finn(23, 5), Personaje.Jake(25, 4))


    var tiempoDeSimulacion = 0
    private val TIEMPO_A_SIMULAR = 120000

    fun play() {
        println("Primero que nada, toca repartir los equipos, hay dos posibilidades:")
        establecerEquipos()

        do {
            println("El tiempo actual de simulación es: $tiempoDeSimulacion")
            println()
            putMembersIntoTheField()

            while (!areTheyNextToEachOther()) {
                moveMembersRandomly()
                wait1Second()
            }

            while (!isAnyoneDeath()) {
                fight()
                wait1Second(1000)
            }

            // se recarga el maná de los personajes después del combate
            findCharacter(getTeam(Propietario.JUGADOR)).mana = 100
            findCharacter(getTeam(Propietario.ORDENADOR)).mana = 100

            wait1Second()

            limpiarTablero()
        } while (tiempoDeSimulacion <= TIEMPO_A_SIMULAR && t1.teamStillAlive() && t2.teamStillAlive())
    }

    /**
     * Vacia el tablero
     */
    private fun limpiarTablero() {
        for (i in field.indices) {
            for (j in field[i].indices) {
                field[i][j] = null
            }
        }
    }

    private fun fight() {
        val computerCharacter = findCharacter(getTeam(Propietario.ORDENADOR))
        val playerCharacter = findCharacter(getTeam(Propietario.JUGADOR))
        println("Ordenador: $computerCharacter")
        println("Jugador: $playerCharacter")
        println()

        var damagePlayer: Int = 0
        if (playerCharacter.isAlive) {
            println("Jugador, te toca elegir entre las opciones de ataque posibles:")
            val optionPlayer = menuAtaques()
            //Calculamos el daño que hace ese personaje
            damagePlayer = resultsOfTurn(playerCharacter, optionPlayer, computerCharacter)
        } else {
            println("El personajes del jugador a muerto.")
            println()
        }

        var damageComputer: Int = 0
        val optionComputer = (1..2).random() // opcion random
        if (computerCharacter.isAlive) {
            damageComputer = resultsOfTurn(computerCharacter, optionComputer, playerCharacter)

        } else {
            println("No hay turno posible para el ordenador, ya que su personaje, está muerto.")
            println()
        }
        if (playerCharacter.isAlive) {
            println("El jugador, a atacado a: $computerCharacter, con un daño de $damagePlayer puntos de daño.")
            computerCharacter.getHit(damagePlayer)
            println()
        }
        if (computerCharacter.isAlive) {
            println("El ordenador, a atacado a: ${playerCharacter}, con un daño de $damageComputer puntos de daño.")
            playerCharacter.getHit(damageComputer)
            println()
        }
    }


    private fun menuAtaques(): Int {
        println("*********************************")
        println("*             Menú              *")
        println("*********************************")
        println("* [1] Atacar                    *")
        println("* [2] Ejecutar acción especial  *")
        println("*********************************")
        println("Introduzca la opción que desea:")
        return introducirOpcion()
    }


    /**
     * Dado un equipo, la funcion encuentra el personaje de ese equipo en la matriz.
     * @param team equipo del cual se quiere buscar el personaje.
     * @return devuelve el personaje del equipo.
     */
    private fun findCharacter(team: Team): Personaje {
        var personaje: Personaje? = null
        for (i in field.indices) {
            for (j in field[i].indices) {
                if (field[i][j] != null) {
                    if (team.team.contains(field[i][j]!!)) {
                        personaje = field[i][j]
                        break
                    }
                }
                if (personaje != null) break
            }
        }
        return personaje!!
    }


    private fun resultsOfTurn(character: Personaje, opcion: Int, enemy: Personaje): Int {
        var damage: Int = 0
        when (character) {
            is Personaje.Mordekai -> {
                when (opcion) {
                    1 -> damage = character.damage
                    2 -> damage = character.specialAction()
                }
            }

            is Personaje.Rigby -> {
                when (opcion) {
                    1 -> damage = character.damage
                    2 -> character.specialAction()
                }
            }

            is Personaje.Finn -> {
                when (opcion) {
                    1 -> damage = character.damage
                    2 -> damage = character.specialAction(enemy)
                }
            }

            is Personaje.Jake -> {
                when (opcion) {
                    1 -> damage = character.damage
                    2 -> damage = character.specialAction()
                }
            }
        }
        return damage
    }

    /**
     * función que sirve para comprobar si alguno de los miembros están muertos o no
     * @return true si hay alguno muerto, false, si es lo contrario
     */
    private fun isAnyoneDeath(): Boolean {
        for (i in field.indices) {
            for (j in field[i].indices) {
                if (field[i][j] != null && !field[i][j]!!.isAlive) return true
            }
        }
        return false
    }

    /**
     * Función que mueve aleatoriamente los miembros de la matriz
     */
    private fun moveMembersRandomly() {
        val auxiliar: Array<Array<Personaje?>> = Array(tamañoFilasColumnas) { Array(tamañoFilasColumnas) { null } }
        for (i in field.indices) {
            for (j in field[i].indices) {
                if (field[i][j] != null) {
                    var fila: Int
                    var columna: Int
                    do {
                        fila = (0..field.size - 1).random()
                        columna = (0..field[0].size - 1).random()
                    } while (auxiliar[fila][columna] != null)
                    auxiliar[fila][columna] = field[i][j]
                    println("El personaje: ${field[i][j]}, se mueve a la posición $fila-$columna")
                }
            }
        }
        println()
        field = auxiliar
        println("Tras tanto movimiento, el tablero está así:")
        showField()
        println()
    }

    /**
     * función que sirve para que el jugador y el ordenador introduzcan miembros del equipo a la matriz
     */
    private fun putMembersIntoTheField() {
        println("Jugador, se va a posicionar el primer miembro del equipo")
        putPlayersMemberIntoTheField()
        println("Ahora le toca al ordenador colocar a su personaje en el tablero:")
        putComputersMemberIntoTheField()
    }

    private fun putComputersMemberIntoTheField() {
        var t = getTeam(Propietario.ORDENADOR)
        val personaje = t.team.dequeue()
        //Hacemos un enqueue para poder introducir el personaje al final de la cola, para poder usar el contains
        t.team.enqueue(personaje)
        println("Por parte del ordenador, va a salir el miembro: ${personaje}, en la posición ${field.size}-${field[0].size}:")
        field[field.size - 1][field[0].size - 1] = personaje
        showField()
        println()
    }

    /**
     * función que sirve para colocar los miembros del equipo del jugador
     */
    private fun putPlayersMemberIntoTheField() {
        var t = getTeam(Propietario.JUGADOR)
        val personaje = t.team.dequeue()
        //Hacemos un enqueue para poder introducir el personaje al final de la cola, para poder usar el contains
        t.team.enqueue(personaje)
        println("De acuerdo, el miembro: ${personaje}, salio a la matriz en la posición 1-1:")
        field[0][0] = personaje
        showField()
        println()
    }

    /**
     * función que sirve para mostrar el tablero
     */
    private fun showField() {
        var mensaje = ""
        for (i in field.indices) {
            for (j in field[i].indices) {
                when (field[i][j]) {
                    is Personaje.Mordekai -> mensaje += " [M]"
                    is Personaje.Rigby -> mensaje += " [R]"
                    is Personaje.Finn -> mensaje += " [F]"
                    is Personaje.Jake -> mensaje += " [J]"
                    else -> mensaje += " [ ]"
                }
            }
            println(mensaje)
            mensaje = ""
        }
    }

    /**
     * función que sirve para comprobar si los personajes están al lado el uno del otro o no
     * @return true en caso de que lo estén, false en caso contrario
     */
    private fun areTheyNextToEachOther(): Boolean {
        for (i in field.indices) {
            for (j in field[i].indices) {
                if (field[i][j] != null && field[i][j] is Personaje) {
                    for (a in -1..1) {
                        for (b in -1..1) {
                            if (i + a >= 0 && i + a < field.size && j + b >= 0 && j + b < field[i].size) {
                                if (field[i + a][j + b] != field[i][j] && field[i + a][j + b] is Personaje) {
                                    return true
                                }
                            }
                        }
                    }
                }
                if (field[i][j] != null && field[i][j] is Personaje) break
            }
        }
        return false
    }

    /**
     * función que sirve para esperar un segundo en la simulación, y aumentar en eso mismo el contador de tiempo
     * @return 1 pues es el tiempo que pasa
     */
    fun wait1Second(sumaAdicional: Int = 0) {
        Thread.sleep(1000)
        tiempoDeSimulacion += 1000 + sumaAdicional
        println("El tiempo actual de simulación es: $tiempoDeSimulacion")
        println()
    }


    /**
     * Function que averigua el equipo del propietario que se le pasa por parámetro
     * @param propietario es el propietario del cual se quiere buscar el equipo
     * @return el equipo del jugador
     */

    fun getTeam(propietario: Propietario): Team {
        var t = t1
        if (t.propietario != propietario) {
            t = t2
        }
        return t
    }

    /**
     * Función que nos permite establecer los propietarios de los equipos
     */
    private fun establecerEquipos() {
        println("El equipo 1 es:")
        t1.showTeam()
        println()
        println("El equipo 2 es:")
        t2.showTeam()
        println()
        println("Jugador, te toca elegir el equipo que deseas:")
        when (menuEquipos()) {
            1 -> {
                t1.propietario = Propietario.JUGADOR
                t2.propietario = Propietario.ORDENADOR
            }

            2 -> {
                t1.propietario = Propietario.ORDENADOR
                t2.propietario = Propietario.JUGADOR
            }
        }
        println()
    }

    /**
     * Función que muestra el menú de opciones, y devuelve la desead
     * @return la opción seleccionada por el usuario
     */
    private fun menuEquipos(): Int {
        println("*****************************")
        println("*           Menú            *")
        println("*****************************")
        println("* [1] Equipo 1              *")
        println("* [2] Equipo 2              *")
        println("*****************************")
        println("Introduzca la opción que deseé")
        return introducirOpcion()
    }

    /**
     * función que sirve para introducir la opcion para elegir el equipo deseado
     * @return la opción seleccionada por el usuario
     */
    private fun introducirOpcion(): Int {
        var opcion: String
        do {
            try {
                opcion = readln().trim()
                validateOption(opcion)
            } catch (e: Exception) {
                println(e.message)
                opcion = ""
            }
        } while (opcion == "")
        return opcion.toInt()
    }

    /**
     * Función que sirve para verificar si la opción introducida es válida o no
     * @return true si la opción es válida
     * @throws IllegalArgumentException si la opción es inválida
     */
    private fun validateOption(opcion: String): Boolean {
        // comprueba que no esté vacío
        require(opcion.isNotEmpty()) { "Error del sistema: La opción introducida no puede estar vacia, vuelve a probar:" }
        val regex = Regex("-?[0-9]+")
        // comprueba que sea un numero
        require(opcion.matches(regex)) { "Error del sistema: La opción introducida no es un número, vuelve a probar:" }
        val option = opcion.toInt()
        //comprueba que el número introducido esté dentro de los valores aceptados
        require(option in 1..2) { "Error del sistema: La opción introducida no una de las opciones válidas, vuelve a probar:" }

        return true
    }
}