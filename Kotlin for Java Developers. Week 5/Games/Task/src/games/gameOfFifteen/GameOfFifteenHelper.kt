package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {

    fun outOfOrder(x: Int, y: Int) : Boolean {

        return (x < y) && (permutation[x] > permutation[y])
    }

    val range = 0..(permutation.size-1)

    val allPairs = range.map {x -> range.map { y -> Pair(x,y) }}.flatten()

    val outOfOrderCount = allPairs.count { p -> outOfOrder(p.first, p.second) }

    return (outOfOrderCount % 2 == 0)
}