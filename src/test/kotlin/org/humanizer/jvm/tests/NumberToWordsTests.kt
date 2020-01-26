package org.humanizer.jvm.tests

import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import org.humanizer.jvm.toWords
import java.util.*

class EnglishNumberToWordsTest : BehaviorSpec({
    Given("an english locale") {
        val currentLocale = Locale.getDefault()
        Locale.setDefault(Locale("en", "US"))
        
        When("numberToWords for 1") {
            val word = 1.toWords()
            Locale.setDefault(currentLocale)
            Then("it should be an English \"one\"") {
                word shouldBe "one"
            }
        }
    }
})

class DutchNumberToWordsTest : BehaviorSpec({
    Given("a dutch locale") {
        val currentLocale = Locale.getDefault()
        Locale.setDefault(Locale("nl", "BE"))
        
        When("numberToWords for 1") {
            val word = 1.toWords()
            Locale.setDefault(currentLocale)
            Then("it should be a Dutch \"een\"") {
                word shouldBe "een"
            }
        }
    }
})
