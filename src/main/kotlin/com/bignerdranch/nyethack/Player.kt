package com.bignerdranch.nyethack

class Player {

    var name = "madrigal"
        get() = field.replaceFirstChar { it.uppercase() }
        private set(value) { field = value.trim() }

    val title: String
        get() = when {
            name.all { it.isDigit() } -> "The Identifiable"     // Проверка на числа
            name.none { it.isLetter() } -> "The Witness Protection Member"  // Проверка на буквы
            name.count { it.lowercase() in "aeiou" } > 4 -> "The Master of Vowel"   // Проверка на нижний регистр
            name.all { it.isUpperCase() } -> "The Incredible!"  // Проверка на верхний регистр
            name.all { name.length > 8 } -> "The Spacious"      // Проверка на длину символов ( The Spacious - Пространный )

            else -> "The Reonwed Hero"
        }

    fun castFireball (numFireballs: Int = 2) {
        narrate("A glass Fireball springs into existence (x$numFireballs) ")
    }

    fun changeName(newName: String) {
        narrate("$name legally changes their name to $newName")
        name = newName
    }

}