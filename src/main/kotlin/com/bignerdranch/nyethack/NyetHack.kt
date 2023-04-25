package com.bignerdranch.nyethack

import kotlin.random.Random
import kotlin.system.exitProcess

lateinit var player: Player
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


    narrate("Welcome to NyetHack!")
    val playerName = promtHeroName()
    player = Player(playerName)

    Game.play()

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

object Game {

    private val worldMap = listOf(
        listOf(
            TownSquare(),
            Tavern(),
            Room("Back Room")
        ),
        listOf(
            MonsterRoom("A long Corridor"),
            Room("A Generic Room")
        ),
        listOf(
            MonsterRoom("The Dungeon"),
            MonsterRoom("The Dragon Lair", Dragon())
        )
    )

    var gameMap = mutableListOf(
        mutableListOf("0", "0", "0"),
        mutableListOf("0", "0"),
        mutableListOf("0", "0")
    )

    private var currentRoom: Room = worldMap[0][0]
    private var currentPosition = Coordinate(0, 0)

    init {
        narrate("Welcome, adventurer")
        val mortality = if (player.isImmortal) "an Immortal" else "a mortal"
        narrate("${player.name}, $mortality, has ${player.healthPoints} health points")
    }

    fun play() {
        while (true) {
            // Playing NyetHack
            narrate("${player.name} of ${player.hometown}, ${player.title}, is in ${currentRoom.description()}")
            currentRoom.enterRoom()

            print("> Enter your command: ")
            GameInput(readLine()).proccessCommand()


        }
    }

    fun move(direction: Direction) {
        val newPosition = direction.updateCoordinate(currentPosition)
        val newRoom = worldMap.getOrNull(newPosition.y)?.getOrNull(newPosition.x)

        if (newRoom != null) {
            narrate(makeYellow("The hero moves ${direction.name}"))
            currentPosition = newPosition
            currentRoom = newRoom
        } else {
            narrate(makeYellow("You cannot move ${direction.name}"))
        }
    }

    fun fight() {
        val monsterRoom = currentRoom as? MonsterRoom
        val currentMonster = monsterRoom?.monster
        if (currentMonster == null) {
            narrate("There's nothing to fight here")
            return
        }

        while (player.healthPoints > 0 && currentMonster.healthPoints > 0) {
            player.attack(currentMonster)
            Thread.sleep(500)
            if (currentMonster.healthPoints > 0) {
                currentMonster.attack(player)
            }
            Thread.sleep(1000)
        }

        if (player.healthPoints <= 0) {
            narrate(makeYellow("You have been defeated! Thanks for playing"))
            exitProcess(0)
        } else {
            narrate(makeYellow("${currentMonster.name} has been defeated"))
            monsterRoom.monster = null
        }
    }

    fun takeLoot() {
        val loot = currentRoom.lootBox.takeLoot()
        if (loot == null) {
            narrate("${player.name} approaches the loot box, but it is empty")
        } else {
            narrate("${player.name} now has a ${loot.name}")
            player.inventory += loot
        }
    }

    fun sellLoot() {
        when (val currentRoom = currentRoom) {
            is TownSquare -> {
                player.inventory.forEach { item ->
                    if (item is Sellable) {
                        val sellPrice = currentRoom.sellLoot(item)
                        narrate("Sold ${item.name}")
                        player.gold += sellPrice
                    } else {
                        narrate("Your ${item.name} can't be sold")
                    }
                }
                player.inventory.removeAll { it is Sellable}
            }
            else -> narrate("You cannot sell anything here")
        }
    }

    private class GameInput(arg: String?) {
        private val input = arg ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1) { "" }

        val townSquare = TownSquare()

        fun proccessCommand() = when (command.lowercase()) {
            "hp" -> narrate(makeYellow("Player has left ${player.healthPoints} HP"))
            "fight" -> fight()
            "move" -> {
                val direction = Direction.values()
                    .firstOrNull { it.name.equals(argument, ignoreCase = true) }
                if (direction != null) {
                    move(direction)
                } else {
                    narrate(makeYellow("I don't know what direction that is"))
                }
            }
            "take" -> {
                if (argument.equals("loot", ignoreCase = true)) {
                    takeLoot()
                } else {
                    narrate(makeYellow("I don't know what you're trying to take"))
                }
            }

            "sell" -> {
                if (argument.equals("loot", ignoreCase = true)) {
                    sellLoot()
                } else {
                    narrate(makeYellow("I don't know what you're trying to sell"))
                }
            }

            "cast" -> when (argument.lowercase()) {
                "fireball" -> {
                    val temp = Random.nextInt(1, 5)
                    player.castFireball(temp)
                }

                "" -> {
                    narrate(makeYellow("The Hero don't know what to cast"))
                }

                else -> {
                    val temp = Random.nextInt(1, 5)
                    narrate(makeYellow("The Hero casts $argument $temp times"))
                }
            }

            "map" -> {
                showMap()
            }

            "ringbell" -> {
                if (currentRoom.name == "The Town Square") {
                    val temp = Random.nextInt(1, 5)
                    var s = ""
                    repeat(temp) {
                        s += "GWONG "
                    }
                    narrate(makeYellow("The Hero hits the Bell $temp times: $s"))
                } else {
                    narrate(makeYellow("You're not in the Town Square"))
                }
            }

            "prophesize" -> {
                player.prophesize()
            }

            "exit" -> {
                narrate(makeYellow("GoodBye, dear friend. See you soon! "))
                exitProcess(0)
            }

            else -> narrate(makeYellow("I'm not sure what you're trying to do"))
        }
    }

    fun showMap() {
        var x = 0
        var y = 0
        for (rooms in worldMap) {
            for (item in rooms) {
                if (item != currentRoom) {
                    x = worldMap.indexOf(rooms)
                    y = rooms.indexOf(item)
                    gameMap[x][y] = "O"
                } else if (item == currentRoom) {
                    x = worldMap.indexOf(rooms)
                    y = rooms.indexOf(item)
                    gameMap[x][y] = "X"
                }
            }
        }
//        println("$x $y")
        for (itemList in gameMap) {
            println(itemList)
        }
    }
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

