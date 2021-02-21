package board

import board.Direction.*
import java.lang.IllegalArgumentException
import java.util.*

open class SquareBoardImpl(override val width: Int) : SquareBoard {

    val cells = mutableSetOf<Cell>()

    init {
        System.out.println("init square with " + width)
        for(i in 1..width) {
            for (j in 1..width) {
                cells.add(Cell(i,j))
            }
        }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return cells.find { c -> c.i == i && c.j == j }
    }
    override fun getCell(i: Int, j: Int): Cell {
        return getCellOrNull(i, j)?: throw IllegalArgumentException("no such cell")
    }

    override fun getAllCells(): Collection<Cell> {
        return cells.toSet()
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        return jRange.filter { x -> x in 1..width}.map { x -> getCell(i, x) }.toList()
}
    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        return iRange.filter { x -> x in 1..width}.map { x -> getCell(x, j) }.toList()
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        when(direction) {
            UP -> return getCellOrNull(i-1,j)
            DOWN -> return getCellOrNull(i+1, j)
            LEFT -> return getCellOrNull(i, j-1)
            RIGHT -> return getCellOrNull(i, j+1)
        }
    }
}

class GameBoardImpl<T>(width: Int) : GameBoard<T>, SquareBoardImpl(width) {

    val valueMap = mutableMapOf<Cell, T?>()

    init {
        System.out.println("Init game with " + width)
        getAllCells().forEach {c -> valueMap.put(c, null)}
    }

    override operator fun get(cell: Cell): T? {
        return valueMap.get(cell)
    }
    override operator fun set(cell: Cell, value: T?) {
        if(cell in getAllCells()) {
            valueMap.put(cell, value)
        }
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return valueMap.filter { (_, v) -> predicate(v) }.keys
    }
    override fun find(predicate: (T?) -> Boolean): Cell? {
        return valueMap.filter { (_, v) -> predicate(v) }.keys.firstOrNull()
    }
    override fun any(predicate: (T?) -> Boolean): Boolean {
        return valueMap.any { (_,v) -> predicate(v) }
    }
    override fun all(predicate: (T?) -> Boolean): Boolean {
        return valueMap.all { (_,v) -> predicate(v) }
    }
}

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)


fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)

