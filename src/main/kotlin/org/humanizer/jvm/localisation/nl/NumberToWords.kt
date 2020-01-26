@file:Suppress("MemberVisibilityCanBePrivate")

package org.humanizer.jvm.localisation.nl

class NumberToWords {
    
    fun toWords(value: Int): String {
        return toWords(value.toLong())
    }
    
    fun toWords(value: Long): String {
        var number = value
        if (number == 0L)
            return unitsMap[0]
        
        if (number < 0)
            return "min ${toWords(number * -1)}"
        
        var word = ""
        
        for (it in hundreds) {
            val divided = number / it.value
            
            if (divided <= 0)
                continue
            
            word += if (divided == 1L && !it.displayOneUnit)
                it.name
            else
                toWords(divided) + it.prefix + it.name
            
            number %= it.value
            if (number > 0)
                word += it.postfix
        }
        
        if (number > 0) {
            word += if (number < 20)
                unitsMap[number.toInt()]
            else {
                val tens = tensMap[(number / 10).toInt()]
                val unit = number % 10
                if (unit > 0) {
                    val units = unitsMap[unit.toInt()]
                    val trema = units.endsWith("e")
                    units + (if (trema) "ën" else "en") + tens
                } else {
                    tens
                }
            }
        }
        
        return word
    }
    
    fun toOrdinalWords(value: Int): String {
        val word = toWords(value)
        
        if (exceptionNumbersToWords(word) != "")
            return exceptionNumbersToWords(word)
        
        var postFix = "de"
        val endingCharForSte = listOf('t', 'g', 'd')
        endingCharForSte.forEach {
            if (word.lastIndexOf(it) == (word.length - 1))
                postFix = "ste"
        }
        
        return word + postFix
    }
    
    private val unitsMap: List<String> = listOf(
        "nul",
        "een",
        "twee",
        "drie",
        "vier",
        "vijf",
        "zes",
        "zeven",
        "acht",
        "negen",
        "tien",
        "elf",
        "twaalf",
        "dertien",
        "veertien",
        "vijftien",
        "zestien",
        "zeventien",
        "achttien",
        "negentien"
    )
    
    private val tensMap: List<String> = listOf(
        "nul",
        "tien",
        "twintig",
        "dertig",
        "veertig",
        "vijftig",
        "zestig",
        "zeventig",
        "tachtig",
        "negentig"
    )
    
    val ordinalExceptions: Map<String, String> = mapOf(
        "een" to "eerste",
        "drie" to "derde",
        "miljoen" to "miljoenste"
    )
    
    private fun exceptionNumbersToWords(number: String): String =
        if (ordinalExceptions.count { number.endsWith(it.key) } == 1) number.substring(
            0,
            number.length - ordinalExceptions.entries.first { number.endsWith(it.key) }.key.length
        ) + ordinalExceptions.entries.first { number.endsWith(it.key) }.value else ""
    
    data class Fact(
        val value: Int,
        val name: String,
        val prefix: String,
        val postfix: String,
        val displayOneUnit: Boolean
    )
    
    val hundreds: List<Fact> = listOf(
        Fact(
            value = 1000000000,
            name = "miljard",
            prefix = " ",
            postfix = " ",
            displayOneUnit = true
        ),
        Fact(
            value = 1000000,
            name = "miljoen",
            prefix = " ",
            postfix = " ",
            displayOneUnit = true
        ),
        Fact(
            value = 1000,
            name = "duizend",
            prefix = "",
            postfix = " ",
            displayOneUnit = false
        ),
        Fact(
            value = 100,
            name = "honderd",
            prefix = "",
            postfix = "",
            displayOneUnit = false
        )
    )
}
