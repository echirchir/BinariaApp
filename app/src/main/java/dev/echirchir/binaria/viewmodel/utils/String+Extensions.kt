package dev.echirchir.binaria.viewmodel.utils

fun String.binaryToInt() : Int {
    return this.toInt(2)
}

fun String.isBinary(): Boolean {
    return this.all { it == '0' || it == '1' }
}