package games.gameOfFifteen

import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game
import games.game2048.Game2048Initializer

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game = Game15(initializer)

class Game15(private val initializer: GameOfFifteenInitializer) : Game {

    private var permutation: MutableList<Int?> = mutableListOf()
    private val initialPermutation: List<Int> = initializer.initialPermutation

    override fun initialize() {
        initialPermutation.forEach { v -> permutation.add(v) }
        permutation.add(15, null)
    }

    override fun canMove(): Boolean {
       return true
    }

    override fun hasWon(): Boolean {
        return permutation.withIndex().all { p -> checkCorrectPlace(p.index, p.value)}
    }

    fun checkCorrectPlace(index: Int, value: Int?) : Boolean {
        var adjustedValue = (value ?: 0) - 1
        return index == adjustedValue || index == 15
    }

    override fun processMove(direction: Direction) {

        val indexEmpty = permutation.indexOfFirst { v -> v == null }

        if(indexEmpty != null) {

            var switchIndex = -1
            when(direction) {
                Direction.UP -> switchIndex = indexEmpty + 4
                Direction.DOWN -> switchIndex = indexEmpty - 4
                Direction.LEFT -> switchIndex = indexEmpty + 1
                Direction.RIGHT -> switchIndex = indexEmpty - 1
            }

            if(switchIndex >= 0 && switchIndex <= 15 ) {
                permutation[indexEmpty] = permutation[switchIndex]
                permutation[switchIndex] = null
            }
        }
    }

    override fun get(i: Int, j: Int): Int? {

        val index = (i-1) * 4 + j -1
        return permutation[index]
    }

}


