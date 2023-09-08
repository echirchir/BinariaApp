package dev.echirchir.binaria.viewmodel.utils

fun String.binaryToInt() : Int {
    return if(this.isNotEmpty()) this.toInt(2) else 0
}

fun String.isBinary(): Boolean {
    return this.all { it == '0' || it == '1' }
}