import models.TableroJuego

fun main(args: Array<String>) {
    val gameBoard = TableroJuego(args[1].toInt())

    gameBoard.play()
}