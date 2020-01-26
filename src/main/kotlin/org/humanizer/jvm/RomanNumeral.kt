@file:JvmName("RomanNumeralHumanizer")

package org.humanizer.jvm

import org.humanizer.jvm.exceptions.ArgumentException
import org.humanizer.jvm.exceptions.ArgumentOutOfRangeException
import java.util.regex.Pattern

fun Int.toRoman(): String {
    val range = 1..3999
    val maxRomanNumeralLength = 15
    
    if (this !in range) throw ArgumentOutOfRangeException(this, range.first, range.last)
    
    val sb = StringBuilder(maxRomanNumeralLength)
    
    var result = this
    
    romanNumerals.forEach {
        while (result / it.value > 0) {
            sb.append(it.key)
            result -= it.value
        }
    }
    
    return sb.toString()
}

fun String.fromRoman(): Int {
    val input = this.trim().toUpperCase()
    
    val length = input.length
    
    if ((length == 0) || input.isInvalidRomanNumeral()) throw ArgumentException(
        "Empty or invalid Roman numeral string. The input was $input."
    )
    
    var total = 0
    var i = length
    
    while (i > 0) {
        var digit = romanNumerals.entries.single { it.key == input[i - 1].toString() }.value
        
        if (i > 1) {
            val previousDigit = romanNumerals.entries.single { it.key == input[i - 2].toString() }.value
            
            if (previousDigit < digit) {
                digit -= previousDigit
                i--
            }
        }
        
        i--
        total += digit
    }
    
    return total
}

fun String.isInvalidRomanNumeral(): Boolean = !Pattern.compile(
    "^(?i:(?=[MDCLXVI])((M{0,3})((C[DM])|(D?C{0,3}))?((X[LC])|(L?XX{0,2})|L)?((I[VX])|(V?(II{0,2}))|V)?))$",
    Pattern.CASE_INSENSITIVE
).matcher(this).matches()

val romanNumerals: Map<String, Int> = linkedMapOf(
    "M" to 1000,
    "CM" to 900,
    "D" to 500,
    "CD" to 400,
    "C" to 100,
    "XC" to 90,
    "L" to 50,
    "XL" to 40,
    "X" to 10,
    "IX" to 9,
    "V" to 5,
    "IV" to 4,
    "I" to 1
)
