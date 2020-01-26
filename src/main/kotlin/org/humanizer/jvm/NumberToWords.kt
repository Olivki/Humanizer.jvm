@file:JvmName("NumberHumanizer")

package org.humanizer.jvm

import java.util.*

fun Int.toWords(): String = this.toLong().toWords()

fun Long.toWords(): String = when (Locale.getDefault().language) {
    "nl" -> org.humanizer.jvm.localisation.nl.NumberToWords().toWords(this)
    else -> org.humanizer.jvm.localisation.en.NumberToWords().toWords(this)
}

fun Int.toOrdinalWords(): String = when (Locale.getDefault().language) {
    "nl" -> org.humanizer.jvm.localisation.nl.NumberToWords().toOrdinalWords(this)
    else -> org.humanizer.jvm.localisation.en.NumberToWords().toOrdinalWords(this)
}

