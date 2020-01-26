package org.humanizer.jvm.tests

import io.kotlintest.forAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import org.humanizer.jvm.fromRoman
import org.humanizer.jvm.toRoman

class RomanNumeralTests : BehaviorSpec({
    val data = listOf(
        1 to "I",
        2 to "II",
        3 to "III",
        4 to "IV",
        5 to "V",
        6 to "VI",
        7 to "VII",
        8 to "VIII",
        9 to "IX",
        10 to "X",
        11 to "XI",
        12 to "XII",
        40 to "XL",
        50 to "L",
        90 to "XC",
        100 to "C",
        400 to "CD",
        500 to "D",
        3999 to "MMMCMXCIX"
    )
    
    Given("a list of numbers in integer form") {
        forAll(data) { (input, expected) ->
            When("calling toRoman on \"$input\"") {
                Then("it should become \"$expected\"") {
                    input.toRoman() shouldBe expected
                }
            }
        }
    }
    
    Given("a list of roman numerals in string form") {
        forAll(data) { (expected, input) ->
            When("calling fromRoman on \"$input\"") {
                Then("it should become \"$expected\"") {
                    input.fromRoman() shouldBe expected
                }
            }
        }
    }
})