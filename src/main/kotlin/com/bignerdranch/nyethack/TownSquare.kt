package com.bignerdranch.nyethack

class TownSquare : Room("The Town Square") {

    override val status = "Bustling"

    private var bellSound = "GWONG"

    final override fun enterRoom() {
        narrate("The villiagers rally and cheer as the hero enters")
        ringBell()
    }

    public fun ringBell() {
        narrate("The bell tower announces the hero's presence: $bellSound")
    }


}