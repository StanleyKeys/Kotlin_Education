package com.bignerdranch.nyethack

import kotlin.random.Random

interface Fightable {
    val name: String
    var healthPoints: Int
    val diceCount: Int
    val diceSides: Int
    fun takeDamage(damage: Int)
    fun attack(opponent: Fightable) {
        val damageRoll = (0 until diceCount).sumOf {
            Random.nextInt((diceCount + 1) * diceSides)
        }
        narrate("$name deals $damageRoll to ${opponent.name}")
        opponent.takeDamage(damageRoll)
    }
}

abstract class Monster(
    override val name: String,
    val description: String,
    override var healthPoints: Int
) : Fightable {
    override fun takeDamage(damage: Int) {
        healthPoints -= damage
    }
}

class Goblin(
    name: String = "Goblin",
    description: String = "A nasty-looking goblin",
    healthPoints: Int = 50
) : Monster(name, description, healthPoints) {
    override val diceCount = 3
    override val diceSides = 3
}

class Dragon(
    name: String = "Dragon",
    description: String = "An Ancient Dragon",
    healthPoints: Int = 150
) : Monster(name, description, healthPoints) {
    override val diceCount = 5
    override val diceSides = 3
}