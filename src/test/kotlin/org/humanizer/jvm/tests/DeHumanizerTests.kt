package org.humanizer.jvm.tests

import io.kotlintest.forAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import org.humanizer.jvm.dehumanize

class DeHumanizerTests : BehaviorSpec({
    val data = listOf(
        "Pascal case sentence is camelized" to "PascalCaseSentenceIsCamelized",
        "Title Case Sentence Is Camelized" to "TitleCaseSentenceIsCamelized",
        "Mixed case sentence Is Camelized" to "MixedCaseSentenceIsCamelized",
        "lower case sentence is camelized" to "LowerCaseSentenceIsCamelized",
        "" to ""
    )
    
    Given("a list of sentences with different casings") {
        forAll(data) { (input, expected) ->
            When("calling dehumanize on \"$input\"") {
                Then("it should become \"$expected\"") {
                    input.dehumanize() shouldBe expected
                }
            }
        }
    }
})


