package org.humanizer.jvm

fun truncateFixedLength(value: String, length: Int, truncationString: String, truncateFrom: TruncateFrom): String {
    var adjustedLength = length - (truncationString.length - 1)
    var adjustedTruncationString = truncationString
    if (adjustedLength <= 0) {
        adjustedLength = length + 1
        adjustedTruncationString = ""
    }
    return when {
        value.length <= length -> value
        truncateFrom == TruncateFrom.Left -> "$adjustedTruncationString${value.substring(
            value.length - adjustedLength + 1,
            value.length
        )}"
        else -> "${value.substring(0, adjustedLength - 1)}$adjustedTruncationString"
    }
}

fun truncateFixedNumberOfCharacters(
    value: String,
    length: Int,
    truncationString: String,
    truncateFrom: TruncateFrom
): String {
    var adjustedLength = length - (truncationString.length - 1)
    var adjustedTruncationString = truncationString
    if (adjustedLength <= 0) {
        adjustedLength = length + 1
        adjustedTruncationString = ""
    }
    return when {
        length >= value.count { Character.isLetterOrDigit(it) } -> value
        truncateFrom == TruncateFrom.Right -> {
            var trimmedText = ""
            var index = 0
            value.forEach {
                if (Character.isLetterOrDigit(it)) index++
                if (adjustedLength > index) {
                    trimmedText += it
                }
            }
            "$trimmedText$adjustedTruncationString"
        }
        else -> {
            var trimmedText = ""
            var index = 0
            value.reversed().forEach {
                if (Character.isLetterOrDigit(it)) index++
                if (adjustedLength > index) {
                    trimmedText += it
                }
            }
            "$adjustedTruncationString${trimmedText.reversed()}"
        }
    }
}

fun truncateFixedNumberOfWords(
    value: String,
    length: Int,
    truncationString: String,
    truncateFrom: TruncateFrom
): String = when {
    length >= value.split("\\s+".toRegex()).count() -> value
    truncateFrom == TruncateFrom.Right -> {
        var trimmedText = ""
        var index = 0
        value.forEach {
            if (Character.isWhitespace(it)) index++
            if (length > index) {
                trimmedText += it
            }
        }
        "$trimmedText$truncationString"
    }
    else -> {
        var trimmedText = ""
        var index = 0
        value.trimEnd().reversed().forEach {
            if (Character.isWhitespace(it)) index++
            if (length > index) {
                trimmedText += it
            }
        }
        "$truncationString${trimmedText.reversed()}"
    }
}

enum class Truncator {
    FixedLength,
    FixedNumberOfCharacters,
    FixedNumberOfWords
}

enum class TruncateFrom {
    Left,
    Right
}
