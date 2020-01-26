package org.humanizer.jvm.formatters

import org.humanizer.jvm.getDateDiff
import java.util.*
import java.util.concurrent.TimeUnit

class DefaultDateFormatter {
    
    private var suffix: String = "ago"
    
    private fun makeSuffix(difference: Long) {
        suffix = "ago"
        if (difference > 0) suffix = "from now"
    }
    
    fun secondFormatter(input: Date, toCompareAgainst: Date): String {
        var difference = input.getDateDiff(toCompareAgainst, TimeUnit.SECONDS)
        makeSuffix(difference)
        difference = Math.abs(difference)
        return when {
            difference == 1.toLong() -> "one second $suffix"
            difference > 1.toLong() && difference < 60.toLong() -> (difference).toString() + " seconds " + suffix
            else -> "a minute $suffix"
        }
    }
    
    fun minuteFormatter(input: Date, toCompareAgainst: Date): String {
        var difference = input.getDateDiff(toCompareAgainst, TimeUnit.MINUTES)
        makeSuffix(difference)
        difference = Math.abs(difference)
        return when {
            difference == 1.toLong() -> "a minute $suffix"
            difference > 1.toLong() && difference < 60.toLong() -> (difference).toString() + " minutes " + suffix
            else -> "an hour $suffix"
        }
    }
    
    fun hourFormatter(input: Date, toCompareAgainst: Date): String {
        var difference = input.getDateDiff(toCompareAgainst, TimeUnit.HOURS)
        makeSuffix(difference)
        difference = Math.abs(difference)
        return when {
            difference == 1.toLong() -> "an hour $suffix"
            difference > 1.toLong() && difference < 24.toLong() -> (difference).toString() + " hours " + suffix
            else -> "a day $suffix"
        }
    }
    
    fun dayFormatter(input: Date, toCompareAgainst: Date): String {
        val difference = input.getDateDiff(toCompareAgainst, TimeUnit.DAYS)
        makeSuffix(difference)
        return when {
            difference == (-1).toLong() -> "yesterday"
            difference == 1.toLong() -> "tomorrow"
            Math.abs(difference) > 1.toLong() && Math.abs(difference) < 31.toLong() -> (Math.abs(difference)).toString() + " days " + suffix
            else -> "one month $suffix"
        }
    }
    
    fun monthFormatter(input: Date, toCompareAgainst: Date): String {
        var difference = getMonthsDifference(input, toCompareAgainst)
        makeSuffix(difference.toLong())
        difference = Math.abs(difference)
        return when {
            difference == 1 -> "one month $suffix"
            Math.abs(difference) > 1.toLong() && Math.abs(difference) < 12.toLong() -> (Math.abs(difference)).toString() + " months " + suffix
            else -> "one year $suffix"
        }
    }
    
    fun yearFormatter(input: Date, toCompareAgainst: Date): String {
        var difference = input.year - toCompareAgainst.year
        makeSuffix(difference.toLong())
        difference = Math.abs(difference)
        return when (difference) {
            1 -> "one year $suffix"
            else -> (Math.abs(difference)).toString() + " years " + suffix
        }
    }
    
    private fun getMonthsDifference(date1: Date, date2: Date): Int {
        val m1 = date1.year * 12 + date1.month
        val m2 = date2.year * 12 + date2.month
        return m1 - m2
    }
}
