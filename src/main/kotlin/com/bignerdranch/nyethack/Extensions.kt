package com.bignerdranch.nyethack

fun String.addEnthusiasm(enthusiasmLevel: Int = 1) =
    this + "!".repeat(enthusiasmLevel)

fun <T> T.print(): T {
    println(this)
    return this
}

operator fun List<List<Room>>.get(coordinate: Coordinate) =
    getOrNull(coordinate.y)?.getOrNull(coordinate.x)
