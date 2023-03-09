package models

import colas.Cola

class Team(val p1: Personaje, val p2: Personaje) {
    lateinit var propietario: Propietario
    val team = Cola()

    init {
        team.enqueue(p1)
        team.enqueue(p2)
    }

    /**
     * Función que sirve para mostrar los miembros del equipo
     */
    fun showTeam(){
        team.showCola()
    }

    /**
     * función que sirve para comprobar si un equipo sigue vivo, o no
     * @return true, en caso de que siga mínimo un miebro vivo, false en caso contrario
     */
    fun teamStillAlive(): Boolean{
        return team.isTeamStillAlive()
    }
}