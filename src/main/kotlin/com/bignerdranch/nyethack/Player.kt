package com.bignerdranch.nyethack

class Player {

    var name = "madrigal"
        get() = field.replaceFirstChar { it.uppercase() }
        private set(value) { field = value.trim() }

    val title: String
        get() = when {
            name.all { it.isDigit() } -> "The Identifiable"     // �������� �� �����
            name.none { it.isLetter() } -> "The Witness Protection Member"  // �������� �� �����
            name.count { it.lowercase() in "aeiou" } > 4 -> "The Master of Vowel"   // �������� �� ������ �������
            name.all { it.isUpperCase() } -> "The Incredible!"  // �������� �� ������� �������
            name.all { name.length > 8 } -> "The Spacious"      // �������� �� ����� �������� ( The Spacious - ����������� )

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