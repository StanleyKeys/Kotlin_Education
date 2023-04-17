package com.bignerdranch.nyethack




fun main() {
    changeKeyandValue()
}


fun changeKeyandValue() {
    val gradesByStudent = mapOf("John" to 4.0, "Alex" to 2.0, "Jane" to 3.0)
    println("normalMap   is $gradesByStudent")
    val reversedMap = mutableMapOf<Double, String>()
    gradesByStudent.forEach { reversedMap.put(it.value, it.key) }
    println("reversedMap is $reversedMap")

}
