package org.humanizer.jvm.tests

import io.kotlintest.forAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import org.humanizer.jvm.ordinalize

class OrdinalizeStringsTest : BehaviorSpec({
    val data = listOf(
        "0" to "0th",
        "1" to "1st",
        "2" to "2nd",
        "3" to "3rd",
        "4" to "4th",
        "5" to "5th",
        "6" to "6th",
        "7" to "7th",
        "8" to "8th",
        "9" to "9th",
        "10" to "10th",
        "11" to "11th",
        "12" to "12th",
        "13" to "13th",
        "14" to "14th",
        "20" to "20th",
        "21" to "21st",
        "22" to "22nd",
        "23" to "23rd",
        "24" to "24th",
        "100" to "100th",
        "101" to "101st",
        "102" to "102nd",
        "103" to "103rd",
        "104" to "104th",
        "110" to "110th",
        "1000" to "1000th",
        "1001" to "1001st"
    )
    
    Given("a list of numbers in string format") {
        forAll(data) { (input, expected) ->
            When("calling ordinalize on \"$input\" (string)") {
                Then("it should become \"$expected\"") {
                    input.ordinalize() shouldBe expected
                }
            }
        }
    }
})

class OrdinalizeIntegersTest : BehaviorSpec({
    val data = listOf(
        0 to "0th",
        1 to "1st",
        2 to "2nd",
        3 to "3rd",
        4 to "4th",
        5 to "5th",
        6 to "6th",
        7 to "7th",
        8 to "8th",
        9 to "9th",
        10 to "10th",
        11 to "11th",
        12 to "12th",
        13 to "13th",
        14 to "14th",
        20 to "20th",
        21 to "21st",
        22 to "22nd",
        23 to "23rd",
        24 to "24th",
        100 to "100th",
        101 to "101st",
        102 to "102nd",
        103 to "103rd",
        104 to "104th",
        110 to "110th",
        1000 to "1000th",
        1001 to "1001st"
    )
    
    Given("a list of numbers in integer format") {
        forAll(data) { (input, expected) ->
            When("calling ordinalize on \"$input\" (integer)") {
                Then("it should become \"$expected\"") {
                    input.ordinalize() shouldBe expected
                }
            }
        }
    }
})