package com.bignerdranch.nyethack

val player = Player()
fun main() {
    /*
   narrate("A hero enters the town of Kronstadt. What is their name?", ::makeYellow)
   val heroName = readLine() ?: ""

   val heroName = readLine()
   require(heroName != null && heroName.isNotEmpty()) {
       "The hero must have a name."
   }

   changeNarrationMood()
   */

    narrate("${player.name} is ${player.title}")
    player.changeName("Aurelia")

    narrate("${player.name}, ${player.title}, heads to the town square")

    visitTavern()
    player.castFireball()
}

private fun promtHeroName(): String {
    narrate("A hero enters the town of Kronstadt. What is their name?") { message ->
        // Выводит message желтым цветом
        "\u001b[33;1m$message\u001b[0m"
    }
    /*
    val input = readLine()
    require(input != null && input.isNotEmpty()) {
        "The hero must have a name"
    }
    return input
    */
    println("Madrigal")
    return "Madrigal"

}

private fun makeYellow(message: String) = "\u001b[33;1m$message\u001b[0m"

private fun createTitle(name: String): String {
    return when {
        name.all { it.isDigit() } -> "The Identifiable"     // Проверка на числа
        name.none { it.isLetter() } -> "The Witness Protection Member"  // Проверка на буквы
        name.count { it.lowercase() in "aeiou" } > 4 -> "The Master of Vowel"   // Проверка на нижний регистр 
        name.all { it.isUpperCase() } -> "The Incredible!"  // Проверка на верхний регистр
        name.all { name.length > 8 } -> "The Spacious"      // Проверка на длину символов ( The Spacious - Пространный )

        else -> "The Renowed Hero"
    }
}

