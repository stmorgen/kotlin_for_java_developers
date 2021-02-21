package games.game2048


/*
 * This function moves all the non-null elements to the beginning of the list
 * (by removing nulls) and merges equal elements.
 * The parameter 'merge' specifies the way how to merge equal elements:
 * it returns a new element that should be present in the resulting list
 * instead of two merged elements.
 *
 * If the function 'merge("a")' returns "aa",
 * then the function 'moveAndMergeEqual' transforms the input in the following way:
 *   a, a, b -> aa, b
 *   a, null -> a
 *   b, null, a, a -> b, aa
 *   a, a, null, a -> aa, a
 *   a, null, a, a -> aa, a
 *
 * You can find more examples in 'TestGame2048Helper'.
*/
fun <T : Any> List<T?>.moveAndMergeEqual(merge: (T) -> T): List<T>
        {

            val nonNullValues = filterNotNull().windowed(2  , 1, true)

            val result = mutableListOf<T>()

            var index = 0
            while(index < nonNullValues.size) {

                val p = nonNullValues[index]

                if(p.size == 1) {
                    result.add(p[0])
                    index += 1
                } else if(p[0] == p[1]) {
                    result.add(merge(p[0]))
                    index += 2
                } else {
                    result.add(p[0])
                    index += 1
                }
            }
            return result
        }

