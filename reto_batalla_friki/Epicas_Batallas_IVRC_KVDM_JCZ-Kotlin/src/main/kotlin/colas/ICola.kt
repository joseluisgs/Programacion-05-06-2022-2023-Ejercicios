package colas

interface ICola <T>{
    fun enqueue (entity: T)
    fun dequeue (): T
    fun peak(): T
}