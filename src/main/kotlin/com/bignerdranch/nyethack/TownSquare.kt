package com.bignerdranch.nyethack

class TownSquare : Room("The Town Square") {

    override val status = "Bustling"

    private var bellSound = "GWONG"

    val hatDropOffBox = DropOffBox<Hat>()
    val gemDropOffBox = DropOffBox<Gemstones>()


    final override fun enterRoom() {
        narrate("The villiagers rally and cheer as the hero enters")
        ringBell()
    }

    public fun ringBell() {
        narrate("The bell tower announces the hero's presence: $bellSound")
    }

    fun <T> sellLoot(
        loot: T
    ): Int where T : Loot, T : Sellable {
        return when (loot) {
            is Hat -> hatDropOffBox.sellLoot(loot)
            is Gemstones -> gemDropOffBox.sellLoot(loot)
            else -> 0
        }
    }

}