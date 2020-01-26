@file:JvmName("OrdinalHumanizer")

package org.humanizer.jvm

fun String.ordinalize(): String = when {
    this.endsWith("1") && this != "11" -> "${this}st"
    this.endsWith("2") && this != "12" -> "${this}nd"
    this.endsWith("3") && this != "13" -> "${this}rd"
    else -> "${this}th"
}

fun Int.ordinalize(): String = this.toString().ordinalize()

