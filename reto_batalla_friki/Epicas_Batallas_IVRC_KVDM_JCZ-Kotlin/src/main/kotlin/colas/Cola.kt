package colas

import models.Personaje

class Cola: ICola<Personaje> {
    private val lista = mutableListOf<Personaje>()

    override fun enqueue(entity: Personaje) {
        lista.add(entity)
    }

    override fun dequeue(): Personaje {
        return lista.removeFirst()
    }

    override fun peak(): Personaje {
        return lista.first()
    }

    fun showCola(){
        lista.forEach{ println(it)}
    }
    fun isTeamStillAlive(): Boolean {
        for(i in lista){
            if(i.isAlive) return true
        }
        return false
    }
    fun contains(personaje: Personaje): Boolean {
        return lista.contains(personaje)
    }
}