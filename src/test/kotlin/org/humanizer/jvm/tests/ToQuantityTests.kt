package org.humanizer.jvm.tests

import io.kotlintest.forAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import org.humanizer.jvm.ShowQuantityAs
import org.humanizer.jvm.toQuantity

class GenericToQuantityTests : BehaviorSpec({
    val data = listOf(
        QuantityData("case", 0, "0 cases"),
        QuantityData("case", 1, "1 case"),
        QuantityData("case", 5, "5 cases"),
        QuantityData("man", 0, "0 men"),
        QuantityData("man", 1, "1 man"),
        QuantityData("man", 2, "2 men"),
        QuantityData("men", 2, "2 men"),
        QuantityData("process", 2, "2 processes"),
        QuantityData("process", 1, "1 process"),
        QuantityData("processes", 2, "2 processes"),
        QuantityData("processes", 1, "1 process")
    )

    Given("a list of different quantities") {
        forAll(data) { (value, quantity, expected) ->
            When("calling toQuantity on \"$value\" with quantity=$quantity") {
                Then("it should become \"$expected\"") {
                    value.toQuantity(quantity) shouldBe expected
                }
            }
            When("calling toQuantity on quantity $quantity with unit \"$value\"") {
                Then("it should become \"$expected\"") {
                    quantity.toQuantity(value) shouldBe expected
                }
            }
        }
    }
})

class HiddenQuantityToQuantityTests : BehaviorSpec({
    val data = listOf(
        QuantityData("case", 0, "cases"),
        QuantityData("case", 1, "case"),
        QuantityData("case", 5, "cases"),
        QuantityData("man", 0, "men"),
        QuantityData("man", 1, "man"),
        QuantityData("man", 2, "men"),
        QuantityData("men", 2, "men"),
        QuantityData("process", 2, "processes"),
        QuantityData("process", 1, "process"),
        QuantityData("processes", 2, "processes"),
        QuantityData("processes", 1, "process")
    )

    Given("a list of different quantities") {
        forAll(data) { (value, quantity, expected) ->
            When("calling toQuantity with quantity $quantity") {
                Then("it should become \"$expected\"") {
                    value.toQuantity(quantity, ShowQuantityAs.None) shouldBe expected
                }
            }
        }
    }
})

class NumericQuantityToQuantityTests : BehaviorSpec({
    val data = listOf(
        QuantityData("case", 0, "0 cases"),
        QuantityData("case", 1, "1 case"),
        QuantityData("case", 5, "5 cases"),
        QuantityData("man", 0, "0 men"),
        QuantityData("man", 1, "1 man"),
        QuantityData("man", 2, "2 men"),
        QuantityData("men", 2, "2 men"),
        QuantityData("process", 2, "2 processes"),
        QuantityData("process", 1, "1 process"),
        QuantityData("processes", 2, "2 processes"),
        QuantityData("processes", 1, "1 process")
    )

    Given("a list of different quantities") {
        forAll(data) { (value, quantity, expected) ->
            When("calling toQuantity with quantity $quantity") {
                Then("it should become \"$expected\"") {
                    value.toQuantity(quantity, ShowQuantityAs.Numeric) shouldBe expected
                }
            }
        }
    }
})

class WordQuantityToQuantityTests : BehaviorSpec({
    val data = listOf(
        QuantityData("case", 0, "zero cases"),
        QuantityData("case", 1, "one case"),
        QuantityData("case", 5, "five cases"),
        QuantityData("man", 0, "zero men"),
        QuantityData("man", 1, "one man"),
        QuantityData("man", 2, "two men"),
        QuantityData("men", 2, "two men"),
        QuantityData("process", 2, "two processes"),
        QuantityData("process", 1, "one process"),
        QuantityData("processes", 2, "two processes"),
        QuantityData("processes", 1200, "one thousand two hundred processes"),
        QuantityData("processes", 1, "one process")
    )

    Given("a list of different quantities") {
        forAll(data) { (value, quantity, expected) ->
            When("calling toQuantity with quantity $quantity") {
                Then("it should become \"$expected\"") {
                    value.toQuantity(quantity, ShowQuantityAs.Words) shouldBe expected
                }
            }
        }
    }
})

data class QuantityData(
    val value: String,
    val quantity: Int,
    val expected: String,
    val format: String = "",
    val culture: String = ""
) {
    override fun toString(): String = "\"$value\" "
}

/*
        TODO : Make these tests pass and make Gandalf go away.
        data = listOf(
                ParamClass("case", 1, "1 case", ),
                ParamClass("case", 2, "2 cases", "%,d"),
                ParamClass("case", 123456, "123,456 cases","%,d"),
                ParamClass("case", 123456, "123,456.00 cases", "%,2d"),
                ParamClass("dollar", 0, "$0 dollars", "%f"),
                ParamClass("dollar", 1, "$1 dollar", "%f"),
                ParamClass("dollar", 2, "$2 dollars", "%f"),
                ParamClass("dollar", 2, "$2.00 dollars", "%,2f"))

        givenData(data) {
            on("calling truncate with length ${it.quantity}", {
                val actual = it.value.toQuantity(it.quantity, it.format)
                it("should be \"${it.expected}\"", {
                    shouldEqual(it.expected, actual)
                })
            })
        }

        data = listOf(
                ParamClass("case", 0, "0 cases", "N0", "it-IT"),
                ParamClass("case", 1, "1 case", "N0", "it-IT"),
                ParamClass("case", 2, "2 cases", "N0", "it-IT"),
                ParamClass("case", 1234567, "1.234.567 cases", "N0", "it-IT"),
                ParamClass("case", 1234567, "1.234.567,00 cases", "N2", "it-IT"),
                ParamClass("euro", 0, "0 € euros", "C0", "es-ES"),
                ParamClass("euro", 1, "1 € euro", "C0", "es-ES"),
                ParamClass("euro", 2, "2 € euros", "C0", "es-ES"),
                ParamClass("euro", 2, "2,00 € euros", "C2", "es-ES"))

        givenData(data) {
            on("calling toQuantity with quantity ${it.quantity}", {
                val actual = it.value.toQuantity(it.quantity, it.format, it.culture)
                it("should be \"${it.expected}\"", {
                    shouldEqual(it.expected, actual)
                })
            })
        }
        */