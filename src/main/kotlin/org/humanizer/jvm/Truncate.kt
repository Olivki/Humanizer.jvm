@file:JvmName("TruncationHumanizer")

package org.humanizer.jvm

@JvmOverloads
fun String.truncate(
    length: Int,
    truncationString: String = "…",
    truncator: Truncator = Truncator.FixedLength,
    truncateFrom: TruncateFrom = TruncateFrom.Right
): String = when (truncator) {
    Truncator.FixedLength -> truncateFixedLength(this, length, truncationString, truncateFrom)
    Truncator.FixedNumberOfCharacters -> truncateFixedNumberOfCharacters(
        this,
        length,
        truncationString,
        truncateFrom
    )
    Truncator.FixedNumberOfWords -> truncateFixedNumberOfWords(this, length, truncationString, truncateFrom)
}