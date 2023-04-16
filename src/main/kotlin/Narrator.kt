import kotlin.random.Random
import kotlin.random.nextInt

//val narrationModifier = { message: String ->
//    val numExclamationPoints = 3
//    message.uppercase() + "!".repeat(numExclamationPoints)
//}

var narrationModifier: (String) -> String = { it }

inline fun narrate(
    message: String,
    modifier: (String) -> String = { narrationModifier(it) }
) {
    println(modifier(message))
//    println({
//        val numExclamationPoints = 3
//        message.uppercase() + "!".repeat(numExclamationPoints)
//    }())
}

fun changeNarrationMood() {
    val mood: String
    val modifier: (String) -> String
    when (Random.nextInt(1..8)) {
        1 -> {
            mood = "mood"
            modifier = { message ->
                val numExclamationPoints = 3
                message.uppercase() + "!".repeat(numExclamationPoints)
            }
        }

        2 -> {
            mood = "tired"
            modifier = { message ->
                message.lowercase().replace(" ", "... ")
            }
        }

        3 -> {
            mood = "unsure"
            modifier = { message ->
                "$message?"
            }
        }

        4 -> {
            var narrationGiven = 0
            mood = "like sending an itemized bill"
            modifier = { message ->
                narrationGiven++
                "$message.\n(I have narrated $narrationGiven things)"
            }
        }

        5 -> {
            mood = "lazy"
            modifier = {message ->
                val splitText = message.split(" ")
                var s = ""
                for (i in 0..(splitText.size / 2)) {
                    s += splitText[i] + " "
                }
                s
            }
        }

        6 -> {
            mood = "Arcane"
            modifier = { message ->
                message
                    .replace("l", "1")
                    .replace("e", "3")
                    .replace("t", "7")
            }
        }

        7 -> {
            mood = "poetry"
            modifier = { message ->
                val splitText = message.split(" ")
                var s = ""
                for (element in splitText) {
                    val r = Random.nextInt(1..5)

                    var spaces = ""
                    for (j in 1..r) {
                        spaces += " "
                    }
                    s += element + spaces
                }
                s
            }
        }

        else -> {                   // это условие идет после последнего числа "Random.nextInt( 1 .. n+1 )"
            mood = "professional"
            modifier = { message ->
                "$message."
            }
        }
    }

    narrationModifier = modifier
    narrate("The narrator begins to feel $mood")
}