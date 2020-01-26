package org.humanizer.jvm.tests

import io.kotlintest.forAll
import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import org.humanizer.jvm.casing.LetterCasing
import org.humanizer.jvm.casing.checkAllCaps
import org.humanizer.jvm.humanize

// TODO: Add tests for more cases; camelCase, hyphenate-case.

class HumanizePascalCaseTests : BehaviorSpec({
    val data = listOf(
        "PascalCaseInputStringIsTurnedIntoSentence" to "Pascal case input string is turned into sentence",
        "WhenIUseAnInputAHere" to "When I use an input a here",
        "10IsInTheBegining" to "10 is in the begining",
        "NumberIsAtTheEnd100" to "Number is at the end 100",
        "XIsFirstWordInTheSentence" to "X is first word in the sentence",
        "HTML" to "HTML",
        "TheHTMLLanguage" to "The HTML language",
        "HTMLIsTheLanguage" to "HTML is the language",
        "TheLanguageIsHTML" to "The language is HTML",
        "HTML5" to "HTML 5",
        "1HTML" to "1 HTML"
    )
    
    Given("a list of sentences formatted in PascalCase") {
        forAll(data) { (input, expected) ->
            When("calling humanize on \"$input\"") {
                Then("it should become \"$expected\"") {
                    input.humanize() shouldBe expected
                }
            }
        }
    }
})

class HumanizeUnderscoreTests : BehaviorSpec({
    val data = listOf(
        "Underscored_input_string_is_turned_into_sentence" to "Underscored input string is turned into sentence"
    )
    
    Given("a list of sentences where ' ' has been replaced with '_'") {
        forAll(data) { (input, expected) ->
            When("calling humanize on \"$input\"") {
                Then("it should become \"$expected\"") {
                    input.humanize() shouldBe expected
                }
            }
        }
    }
})

class HumanizeToTitleCaseTests : BehaviorSpec({
    val data = listOf(
        "CanReturnTitleCase" to "Can Return Title Case",
        "Can_return_title_Case" to "Can Return Title Case",
        "Title_humanization_Honors_ALLCAPS" to "Title Humanization Honors ALLCAPS"
    )
    
    Given("a list of sentences with different casings") {
        forAll(data) { (input, expected) ->
            When("calling humanize on \"$input\"") {
                Then("it should become \"$expected\"") {
                    input.humanize(LetterCasing.Title) shouldBe expected
                }
            }
        }
    }
})

class HumanizeToLowerCaseTests : BehaviorSpec({
    val data = listOf(
        "CanReturnLowerCase" to "can return lower case",
        "Can_Return_Lower_Case" to "can return lower case",
        "LOWERCASE" to "lowercase"
    )
    
    Given("a list of sentences with different casings") {
        forAll(data) { (input, expected) ->
            When("calling humanize on \"$input\"") {
                Then("it should become \"$expected\"") {
                    input.humanize(LetterCasing.LowerCase) shouldBe expected
                }
            }
        }
    }
})

class HumanizeToSentenceCaseTests : BehaviorSpec({
   val  data = listOf(
        "CanReturnSentenceCase" to "Can return sentence case",
        "Can_Return_Sentence_Case" to "Can return sentence case",
        "" to ""
    )
    
    Given("a list of sentences with different casings") {
        forAll(data) { (input, expected) ->
            When("calling humanize on \"$input\"") {
                Then("it should become \"$expected\"") {
                    input.humanize(LetterCasing.Sentence) shouldBe expected
                }
            }
        }
    }
})

class HumanizeToUpperCaseTests : BehaviorSpec({
    val data = listOf(
        "CanHumanizeIntoUpperCase" to "CAN HUMANIZE INTO UPPER CASE",
        "Can_Humanize_into_Upper_case" to "CAN HUMANIZE INTO UPPER CASE"
    )
    
    Given("a list of sentences with different casings") {
        forAll(data) { (input, expected) ->
            When("calling humanize on \"$input\"") {
                Then("it should become \"$expected\"") {
                    input.humanize(LetterCasing.UpperCase) shouldBe expected
                }
            }
        }
    }
})

class HumanizeCheckCapsTests : BehaviorSpec({
    Given("A word in all caps") {
        When("calling checkAllCaps") {
            Then("it should be true") {
                "HTML".checkAllCaps().shouldBeTrue()
            }
        }
    }

    Given("A word in all lowercase") {
        When("calling checkAllCaps") {
            Then("it should be false") {
                "html".checkAllCaps().shouldBeFalse()
            }
        }
    }
})