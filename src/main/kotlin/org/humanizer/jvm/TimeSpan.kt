@file:JvmName("TimeSpanHumanizer")

package org.humanizer.jvm

@JvmOverloads
fun Int.milliSecondsToTimeSpan(milliSecondPrecision: Boolean = false): String =
    this.toLong().milliSecondsToTimeSpan(milliSecondPrecision)

// Honestly stolen from here http://stackoverflow.com/a/2795991
@JvmOverloads
fun Long.milliSecondsToTimeSpan(milliSecondPrecision: Boolean = false): String {
    val buffer = StringBuffer()
    val diffInSeconds = this / 1000
    val milliseconds = this % 1000
    val seconds = if (diffInSeconds >= 60) diffInSeconds % 60 else diffInSeconds
    val minutes = if ((diffInSeconds / 60) >= 60) (diffInSeconds / 60) % (60) else diffInSeconds / 60
    val hours = if ((diffInSeconds / 3600) >= 24) (diffInSeconds / 3600) % (24) else diffInSeconds / 3600
    val days = diffInSeconds / 60 / 60 / 24
    
    if (days > 0) {
        with(buffer) {
            append("day".toQuantity(days))
            append(" and ")
        }
    }
    
    if (hours > 0 || days > 0) {
        with(buffer) {
            append("hour".toQuantity(hours))
            append(" and ")
        }
    }
    
    if (minutes > 0 || hours > 0 || days > 0) {
        with(buffer) {
            append("minute".toQuantity(minutes))
            append(" and ")
        }
    }
    
    buffer.append("second".toQuantity(seconds))
    
    if (milliSecondPrecision) {
        with(buffer) {
            append(" and ")
            append("millisecond".toQuantity(milliseconds))
        }
    }
    
    return buffer.toString()
}