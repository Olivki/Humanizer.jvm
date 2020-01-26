@file:JvmName("PascalCaseHumanizer")

package org.humanizer.jvm

import org.humanizer.jvm.casing.*

@JvmOverloads
fun String.humanize(letterCasing: LetterCasing = LetterCasing.Sentence): String = when (letterCasing) {
    LetterCasing.LowerCase -> this.letterCasingLowerCase()
    LetterCasing.UpperCase -> this.letterCasingUpperCase()
    LetterCasing.Title -> this.letterCasingTitle()
    LetterCasing.Sentence -> this.letterCasingSentence()
}