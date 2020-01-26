package org.humanizer.jvm.tests

import io.kotlintest.forAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import org.humanizer.jvm.*

class TitleizeTests : BehaviorSpec({
    val data = listOf(
        "some title" to "Some Title",
        "some-title" to "Some Title",
        "sometitle" to "Sometitle",
        "some-title: The begining" to "Some Title: The Begining",
        "some_title:_the_begining" to "Some Title: The Begining",
        "some title: The_begining" to "Some Title: The Begining"
    )
    
    Given("a list of sentences") {
        forAll(data) { (value, expected) ->
            When("calling titleize on \"$value\"") {
                Then("it should be \"$expected\"") {
                    value.titleize() shouldBe expected
                }
            }
        }
    }
})

class DasherizeTests : BehaviorSpec({
    val data = listOf(
        "some_title" to "some-title",
        "some-title" to "some-title",
        "some_title_goes_here" to "some-title-goes-here",
        "some_title and_another" to "some-title and-another"
    )
    
    Given("a list of sentences") {
        forAll(data) { (value, expected) ->
            When("calling dasherize on \"$value\"") {
                Then("it should be \"$expected\"") {
                    value.dasherize() shouldBe expected
                }
            }
        }
    }
})

class HyphenateTests : BehaviorSpec({
    val data = listOf(
        "some_title" to "some-title",
        "some-title" to "some-title",
        "some_title_goes_here" to "some-title-goes-here",
        "some_title and_another" to "some-title and-another"
    )
    
    Given("a list of sentences") {
        forAll(data) { (value, expected) ->
            When("calling hyphenate on \"$value\"") {
                Then("it should be \"$expected\"") {
                    value.hyphenate() shouldBe expected
                }
            }
        }
    }
})

class PascalizeTests : BehaviorSpec({
    val data = listOf(
        "customer" to "Customer",
        "CUSTOMER" to "CUSTOMER",
        "CUStomer" to "CUStomer",
        "customer_name" to "CustomerName",
        "customer_first_name" to "CustomerFirstName",
        "customer_first_name_goes_here" to "CustomerFirstNameGoesHere",
        "customer name" to "Customer name"
    )
    
    Given("a list of sentences") {
        forAll(data) { (value, expected) ->
            When("calling pascalize on \"$value\"") {
                Then("it should be \"$expected\"") {
                    value.pascalize() shouldBe expected
                }
            }
        }
    }
})

class CamelizeTests : BehaviorSpec({
    val data = listOf(
        "customer" to "customer",
        "CUSTOMER" to "cUSTOMER",
        "CUStomer" to "cUStomer",
        "customer_name" to "customerName",
        "customer_first_name" to "customerFirstName",
        "customer_first_name_goes_here" to "customerFirstNameGoesHere",
        "customer name" to "customer name"
    )
    
    Given("a list of sentences") {
        forAll(data) { (value, expected) ->
            When("calling camelize on \"$value\"") {
                Then("it should be \"$expected\"") {
                    value.camelize() shouldBe expected
                }
            }
        }
    }
})

class UnderscoreTests : BehaviorSpec({
    val data = listOf(
        "SomeTitle" to "some_title",
        "someTitle" to "some_title",
        "some title" to "some_title",
        "some title that will be underscored" to "some_title_that_will_be_underscored",
        "SomeTitleThatWillBeUnderscored" to "some_title_that_will_be_underscored"
    )
    
    Given("a list of sentences") {
        forAll(data) { (value, expected) ->
            When("calling underscore on \"$value\"") {
                Then("it should be \"$expected\"") {
                    value.underscore() shouldBe expected
                }
            }
        }
    }
})