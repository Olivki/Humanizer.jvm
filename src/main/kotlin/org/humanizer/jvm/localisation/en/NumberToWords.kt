@file:Suppress("MemberVisibilityCanBePrivate")

package org.humanizer.jvm.localisation.en

import java.util.*

class NumberToWords {
    
    fun toWords(value: Int): String {
        return toWords(value.toLong())
    }
    
    fun toWords(value: Long): String {
        var number = value
        if (number == 0L)
            return "zero"
        
        if (number < 0)
            return "minus ${toWords((number * -1))}"
        
        val parts = ArrayList<String>()
        
        if ((number / 1000000000) > 0) {
            parts.add("${toWords((number / 1000000000))} billion")
            number %= 1000000000
        }
        
        if ((number / 1000000) > 0) {
            parts.add("${toWords((number / 1000000))} million")
            number %= 1000000
        }
        
        if ((number / 1000) > 0) {
            parts.add("${toWords((number / 1000))} thousand")
            number %= 1000
        }
        
        if ((number / 100) > 0) {
            parts.add("${toWords(number / 100)} hundred")
            number %= 100
        }
        
        if (number > 0) {
            if (parts.count() != 0)
                parts.add("and")
            
            if (number < 20)
                parts.add(unitsMap[number.toInt()])
            else {
                var lastPart = tensMap[number.toInt() / 10]
                if ((number % 10) > 0)
                    lastPart += "-${unitsMap[number.toInt() % 10]}"
                
                parts.add(lastPart)
            }
        }
        
        return parts.joinToString(" ").trimStart()
    }
    
    fun toOrdinalWords(value: Int): String {
        val toWords: String
        // 9 => ninth
        if (exceptionNumbersToWords(value) != "")
            return exceptionNumbersToWords(value)
        
        // 21 => twenty first
        if (value > 20) {
            val exceptionPart: String
            if (exceptionNumbersToWords(value % 10) != "") {
                exceptionPart = exceptionNumbersToWords(value % 10)
                val normalPart = value - value % 10
                toWords = removeOnePrefix(toWords(normalPart))
                return "$toWords $exceptionPart"
            }
        }
        
        return normalNumberToWords(value)
    }
    
    private val unitsMap: List<String> = listOf(
        "zero",
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven",
        "eight",
        "nine",
        "ten",
        "eleven",
        "twelve",
        "thirteen",
        "fourteen",
        "fifteen",
        "sixteen",
        "seventeen",
        "eighteen",
        "nineteen"
    )
    
    private val tensMap: List<String> =
        listOf("zero", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety")
    
    val ordinalExceptions: Map<Int, String> = mapOf(
        1 to "first",
        2 to "second",
        3 to "third",
        4 to "fourth",
        5 to "fifth",
        8 to "eighth",
        9 to "ninth",
        12 to "twelfth"
    )
    
    private fun normalNumberToWords(number: Int): String {
        var toWords = toWords(number).replace('-', ' ').trimStart()
        
        toWords = removeOnePrefix(toWords)
        // twenty => twentieth
        if (toWords.endsWith("y"))
            toWords = "${toWords.substring(0, toWords.length - 1)}ie"
        
        return "${toWords}th"
    }
    
    private fun removeOnePrefix(toWords: String): String {
        // one hundred => hundredth
        var result = toWords
        
        if (toWords.startsWith("one ")) result = toWords.substring(4)
        
        return result
    }
    
    private fun exceptionNumbersToWords(number: Int): String =
        if (ordinalExceptions.count { it.key == number } == 1)
            ordinalExceptions.entries.first {
                it.key == number
            }.value else ""
}
