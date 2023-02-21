fun main(args: Array<String>) {
    val list1 = listOf<Int>(1,2,3,4,5)
    val list2 = listOf<Int>(1,2,3,4,5,6,7,8,9)

    //Una pequeña prueba con respecto a una duuda que me surgió
    list1 + list2
    list1 plus list2
}

infix fun <T> List<T>.plus(other: List<T>): List<T>{
    return this + other
}