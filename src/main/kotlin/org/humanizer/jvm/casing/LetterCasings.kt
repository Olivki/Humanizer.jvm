@file:JvmName("CasingHumanzier")

package org.humanizer.jvm.casing

const val regexPattern = "(?<=[a-z])(?=[A-Z0-9])|(?<=[0-9])(?=[A-Za-z])|(?<=[A-Z])(?=[0-9])|(?<=[A-Z])(?=[A-Z][a-z])"

val sentenceRegex = Regex(regexPattern)
val dashRegex = Regex("$regexPattern|(_)|(-)")

@JvmOverloads
@Deprecated("Should not be a function", ReplaceWith("regex"))
fun letterCasingRegex(suffix: String = ""): String =
    "(?<=[a-z])(?=[A-Z0-9])|(?<=[0-9])(?=[A-Za-z])|(?<=[A-Z])(?=[0-9])|(?<=[A-Z])(?=[A-Z][a-z])$suffix"

fun String.checkAllCaps(): Boolean = this.all { it.isUpperCase() }

fun String.letterCasingUpperCase(): String = this.replace(dashRegex, " $0").trim().replace("_", "").toUpperCase()

@Suppress("RedundantLambdaArrow")
fun String.letterCasingTitle(): String = this.split(dashRegex).joinToString(" ") {
    if (it.checkAllCaps() && it.length > 1) it else it.capitalize()
}

fun String.letterCasingLowerCase(): String = this.replace(dashRegex, " $0").trim().replace("_", "")
    .toLowerCase()

fun String.letterCasingSentence(): String = this.replace("_", " ").split(sentenceRegex).joinToString(" ") {
    if (it.checkAllCaps() && it.length > 1) it else it.toLowerCase()
}.capitalize().replace(" i ", " I ")

enum class LetterCasing {
    Title,
    UpperCase,
    LowerCase,
    Sentence
}