package mastermind


data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    var rightPosition = 0;
    var wrongPosition = 0;

    

    for (x in 'A'..'F') {

        var rightLetterPosition = 0;
        for ((index, value) in secret.toCharArray().withIndex()) {
            if(value == x && value == guess.toCharArray()[index]) {
                rightLetterPosition++;
            }
        }
        val letterInSecret = secret.count { l -> l == x }
        val letterInGuess = guess.count { l -> l == x }
        rightPosition += rightLetterPosition;
        wrongPosition += Math.max((Math.min(letterInSecret, letterInGuess) - rightLetterPosition), 0);
    }


    /*for ((index, value) in guess.toCharArray().withIndex()) {
        if(value == secret.toCharArray()[index]) {
            rightPosition++;
        } else if(guess.indexOf(value) == index && secret.contains(value, true)) {
            wrongPosition++;
        }
    }*/
    return Evaluation(rightPosition, wrongPosition);
}
