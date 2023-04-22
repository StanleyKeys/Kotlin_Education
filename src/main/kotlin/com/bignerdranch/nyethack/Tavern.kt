package com.bignerdranch.nyethack

import java.io.File
import kotlin.random.Random
import kotlin.random.nextInt

private const val TAVERN_MASTER = "Taernyl"
private const val TAVERN_NAME = "$TAVERN_MASTER's Folly"

private val firstNames = setOf("Alex", "Mordoc", "Sophie", "Tariq")
private val lastNames = setOf("Ironfoot", "Fernsworth", "Baggins", "Downstrider")


private val menuData = File("data/tavern-menu-data.txt")
    .readText()
    .split("\n")
    .map { it.split(",") }


private val menuItems = menuData.map { (_, name, _) -> name }

private val menuItemPrices = menuData.associate { (_, name, price) -> name to price.toDouble() }

private val menuItemTypes = menuData.associate { (type, name, _) -> name to type }



class Tavern : Room(TAVERN_NAME) {
    val patrons: MutableSet<String> = firstNames.shuffled()
        .zip(lastNames.shuffled()) { firstName, lastName -> "$firstName $lastName"}
        .toMutableSet()

    val patronGold: MutableMap<String, Double> = mutableMapOf(
        TAVERN_MASTER to 86.00,
        player.name to 4.50,
        *patrons.map { it to 6.00 }.toTypedArray()
    )

    val itemOfDay = patrons.flatMap { getFavoriteMenuItems(it) }.random()

    override val status = "Busy"
    override fun enterRoom() {
        narrate("${player.name} enters $TAVERN_NAME")
//    narrate("There are several items for sale: ")
        //showMealMenu()
        narrate("The item of the day is the $itemOfDay")

        narrate("${player.name} sees several patrons in the tavern: ")
        narrate(patrons.joinToString())

        placeOrder2(patrons.random())
//        repeat(3) {
//            placeOrder2(patrons.random())
//        }
        //displayPatronBalances(patronGold)
//        patrons
//            .filter { patron -> patronGold.getOrDefault(patron, 0.0) < 4.0 }
//            .also { departingPatrons ->
//                patrons -= departingPatrons
//                patronGold -= departingPatrons
//            }
//            .forEach { patron ->
//                narrate("${player.name} sees $patron departing the Tavern")
//            }
//        narrate("There are still some patrons in the tavern")
//        narrate(patrons.joinToString())
    }

    private fun placeOrder2(patronName: String) {

        narrate("$patronName speaks with $TAVERN_MASTER to place an order")
        val compositeOrder = mutableListOf<String>()
        var counter = 0
        val r = (1..3).random()
        var summ = 0.0
        while (counter < r) {
            val menuItemName = menuItems.random()
            val itemPrice = menuItemPrices.getValue(menuItemName)
            compositeOrder.add(menuItemName)
            summ = summ + itemPrice
            counter += 1
        }
        if (summ <= patronGold.getOrDefault(patronName, 0.0)) {
            for (item in compositeOrder) {
                val action = when (menuItemTypes[item]) {
                    "shandy", "elixir" -> "pours"
                    "meal" -> "serves"
                    else -> "hands"
                }
                narrate("$TAVERN_MASTER $action $patronName a $item")
            }
            narrate("$patronName pays $TAVERN_MASTER $summ gold\n")
            patronGold[patronName] = patronGold.getValue(patronName) - summ
            patronGold[TAVERN_MASTER] = patronGold.getValue(TAVERN_MASTER) + summ
        } else {
            narrate("$TAVERN_MASTER says, \"You need more coin for such big Order\"\n")
        }
    }
}


fun showMealMenu() {                                                     // Метод выведения упорядоченного меню
    println("*** Welcome to $TAVERN_NAME ***")
    val menuData = File("data/tavern-menu-data.txt") // из файла "tavern-menu-data.txt"
        .readText()                                                      // c помощью итерации и добавления "."
        .split("\n")
    for (item in menuData) {
        val itemInfo = item.split(",")

        var temp = itemInfo[1]
        while (temp.count() < 35) {
            temp = temp + "."
        }
        temp = temp + itemInfo[2]
        println(temp)
    }
}

private fun getFavoriteMenuItems(patron: String): List<String> {
    return when (patron) {
        "Alex Ironfoot" -> menuItems.filter { menuItem ->
            menuItemTypes[menuItem]?.contains("dessert") == true
        }

        else -> menuItems.shuffled().take(Random.nextInt(1..2))
    }
}



private fun placeOrder(
    patronName: String,
    menuItemName: String,
    patronGold: MutableMap<String, Double>
) {
    val itemPrice = menuItemPrices.getValue(menuItemName)
    narrate("$patronName speaks with $TAVERN_MASTER to place an order")
    if (itemPrice <= patronGold.getOrDefault(patronName, 0.0)) {
        val action = when (menuItemTypes[menuItemName]) {
            "shandy", "elixir" -> "pours"
            "meal" -> "serves"
            else -> "hands"
        }
        narrate("$TAVERN_MASTER $action $patronName a $menuItemName")
        narrate("$patronName pays $TAVERN_MASTER $itemPrice gold")
        patronGold[patronName] = patronGold.getValue(patronName) - itemPrice
        patronGold[TAVERN_MASTER] = patronGold.getValue(TAVERN_MASTER) + itemPrice
    } else {
        narrate("$TAVERN_MASTER says, \"You need more coin for a $menuItemName\"")
    }

    //narrate("$TAVERN_MASTER hands $patronName a $menuItemName\n")
}

private fun displayPatronBalances(patronGold: Map<String, Double>) {
    narrate("${player.name} intuitively knows how much money each patron has")
    patronGold.forEach { (patron, balance) ->
        narrate("$patron has ${"%.2f".format(balance)} gold")
    }
}