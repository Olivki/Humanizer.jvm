package org.humanizer.jvm.tests

import io.kotlintest.forAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import org.humanizer.jvm.TruncateFrom
import org.humanizer.jvm.Truncator
import org.humanizer.jvm.truncate

class StandardTruncateTests : BehaviorSpec({
    val data = listOf(
        TruncateData("", 10, ""),
        TruncateData("a", 1, "a"),
        TruncateData("Text longer than truncate length", 10, "Text long…"),
        TruncateData("Text with length equal to truncate length", 41, "Text with length equal to truncate length"),
        TruncateData("Text smaller than truncate length", 34, "Text smaller than truncate length")
    )
    
    Given("a list of sentences") {
        forAll(data) { (value, length, expected) ->
            When("calling truncate with length=$length") {
                Then("it should become \"$expected\"") {
                    value.truncate(length) shouldBe expected
                }
            }
        }
    }
})

class TruncateWithFixedLengthTests : BehaviorSpec({
    val data = listOf(
        TruncateData("", 10, ""),
        TruncateData("a", 1, "a"),
        TruncateData("Text longer than truncate length", 10, "Text long…"),
        TruncateData("Text with length equal to truncate length", 41, "Text with length equal to truncate length"),
        TruncateData("Text smaller than truncate length", 34, "Text smaller than truncate length")
    )
    
    Given("a list of sentences") {
        forAll(data) { (value, length, expected) ->
            When("calling truncate with length=$length and truncator=FixedLength") {
                Then("it should become \"$expected\"") {
                    value.truncate(length, truncator = Truncator.FixedLength) shouldBe expected
                }
            }
        }
    }
})

class TruncateWithFixedNumberOfCharactersTests : BehaviorSpec({
    val data = listOf(
        TruncateData("", 10, ""),
        TruncateData("a", 1, "a"),
        TruncateData("Text with more characters than truncate length", 10, "Text with m…"),
        TruncateData(
            "Text with number of characters equal to truncate length",
            47,
            "Text with number of characters equal to truncate length"
        ),
        TruncateData(
            "Text with less characters than truncate length",
            41,
            "Text with less characters than truncate length"
        )
    )
    
    Given("a list of sentences") {
        forAll(data) { (value, length, expected) ->
            When("calling truncate with length=$length and truncator=FixedNumberOfCharacters") {
                Then("it should become \"$expected\"") {
                    value.truncate(length, truncator = Truncator.FixedNumberOfCharacters) shouldBe expected
                }
            }
        }
    }
})

class TruncateWithFixedNumberOfWordsTests : BehaviorSpec({
    val data = listOf(
        TruncateData("", 10, ""),
        TruncateData("a", 1, "a"),
        TruncateData("Text with more words than truncate length", 4, "Text with more words…"),
        TruncateData(
            "Text with number of words equal to truncate length",
            9,
            "Text with number of words equal to truncate length"
        ),
        TruncateData("Text with less words than truncate length", 8, "Text with less words than truncate length"),
        TruncateData("Words are\nsplit\rby\twhitespace", 4, "Words are\nsplit\rby…")
    )
    
    Given("a list of sentences") {
        forAll(data) { (value, length, expected) ->
            When("calling truncate with length=$length and truncator=FixedNumberOfWords") {
                Then("it should become \"$expected\"") {
                    value.truncate(length, truncator = Truncator.FixedNumberOfWords) shouldBe expected
                }
            }
        }
    }
})

class TruncateWithTruncationString : BehaviorSpec({
    val data = listOf(
        TruncateData("", 10, "", "..."),
        TruncateData("a", 1, "a", "..."),
        TruncateData("Text longer than truncate length", 10, "Text lo...", "..."),
        TruncateData(
            "Text with length equal to truncate length",
            41,
            "Text with length equal to truncate length",
            "..."
        ),
        TruncateData("Text smaller than truncate length", 34, "Text smaller than truncate length", "..."),
        TruncateData(
            "Text with delimiter length greater than truncate length truncates to fixed length without truncation string",
            2,
            "Te",
            "..."
        )
    )
    
    Given("a list of sentences") {
        forAll(data) { (value, length, expected, truncationString) ->
            When("calling truncate with length=$length and truncationString=$truncationString") {
                Then("it should become \"$expected\"") {
                    value.truncate(length, truncationString) shouldBe expected
                }
            }
        }
    }
})

class TruncateWithTruncationStringForFixedLengthTests : BehaviorSpec({
    val data = listOf(
        TruncateData("", 10, "", "..."),
        TruncateData("a", 1, "a", "..."),
        TruncateData("Text longer than truncate length", 10, "Text lo...", "..."),
        TruncateData("Text with different truncation string", 10, "Text wi---", "---"),
        TruncateData(
            "Text with length equal to truncate length",
            41,
            "Text with length equal to truncate length",
            "..."
        ),
        TruncateData("Text smaller than truncate length", 34, "Text smaller than truncate length", "..."),
        TruncateData(
            "Text with delimiter length greater than truncate length truncates to fixed length without truncation string",
            2,
            "Te",
            "..."
        )
    )
    
    Given("a list of sentences") {
        forAll(data) { (value, length, expected, truncationString) ->
            When("calling truncate with length=$length, truncationString=$truncationString and truncator=FixedLength") {
                Then("it should become \"$expected\"") {
                    value.truncate(
                        length,
                        truncator = Truncator.FixedLength,
                        truncationString = truncationString
                    ) shouldBe expected
                }
            }
        }
    }
})

class TruncateWithTruncationStringForFixedNumberOfCharactersTests : BehaviorSpec({
    val data = listOf(
        TruncateData("", 10, "", "..."),
        TruncateData("a", 1, "a", "..."),
        TruncateData("Text with more characters than truncate length", 10, "Text wit...", "..."),
        TruncateData("Text with different truncation string", 10, "Text wit---", "---"),
        TruncateData(
            "Text with number of characters equal to truncate length",
            47,
            "Text with number of characters equal to truncate length",
            "..."
        ),
        TruncateData(
            "Text with less characters than truncate length",
            41,
            "Text with less characters than truncate length",
            "..."
        ),
        TruncateData(
            "Text with delimiter length greater than truncate length truncates to fixed length without truncation string",
            2,
            "Te",
            "..."
        )
    )
    
    Given("a list of sentences") {
        forAll(data) { (value, length, expected, truncationString) ->
            When("calling truncate with length=$length, truncationString=$truncationString and truncator=FixedNumberOfCharacters") {
                Then("it should become \"$expected\"") {
                    value.truncate(
                        length,
                        truncator = Truncator.FixedNumberOfCharacters,
                        truncationString = truncationString
                    ) shouldBe expected
                }
            }
        }
    }
})

class TruncateWithTruncationStringForFixedNumberOfWordsTests : BehaviorSpec({
    val data = listOf(
        TruncateData("", 10, "", "..."),
        TruncateData("a", 1, "a", "..."),
        TruncateData("Text with more words than truncate length", 4, "Text with more words...", "..."),
        TruncateData("Text with different truncation string", 4, "Text with different truncation---", "---"),
        TruncateData(
            "Text with number of words equal to truncate length",
            9,
            "Text with number of words equal to truncate length",
            "..."
        ),
        TruncateData(
            "Text with less words than truncate length",
            8,
            "Text with less words than truncate length",
            "..."
        ),
        TruncateData("Words are\nsplit\rby\twhitespace", 4, "Words are\nsplit\rby...", "...")
    )
    
    Given("a list of sentences") {
        forAll(data) { (value, length, expected, truncationString) ->
            When("calling truncate with length=$length, truncationString=$truncationString and truncator=") {
                Then("it should become \"$expected\"") {
                    value.truncate(
                        length,
                        truncator = Truncator.FixedNumberOfWords,
                        truncationString = truncationString
                    ) shouldBe expected
                }
            }
        }
    }
})

class TruncateFromLeftTests : BehaviorSpec({
    val data = listOf(
        TruncateData("", 10, ""),
        TruncateData("a", 1, "a"),
        TruncateData("Text longer than truncate length", 10, "…te length"),
        TruncateData("Text with length equal to truncate length", 41, "Text with length equal to truncate length"),
        TruncateData("Text smaller than truncate length", 34, "Text smaller than truncate length")
    )
    
    Given("a list of sentences") {
        forAll(data) { (value, length, expected) ->
            When("calling truncate with length=$length and truncateFrom=TruncateFrom.Left") {
                Then("it should become \"$expected\"") {
                    value.truncate(length, truncateFrom = TruncateFrom.Left) shouldBe expected
                }
            }
        }
    }
})

class TruncateFromLeftWithFixedLengthTests : BehaviorSpec({
    val data = listOf(
        TruncateData("", 10, ""),
        TruncateData("a", 1, "a"),
        TruncateData("Text longer than truncate length", 10, "…te length"),
        TruncateData("Text with length equal to truncate length", 41, "Text with length equal to truncate length"),
        TruncateData("Text smaller than truncate length", 34, "Text smaller than truncate length")
    )
    
    Given("a list of sentences") {
        forAll(data) { (value, length, expected) ->
            When("calling truncate with length=$length, truncateFrom=TruncateFrom.Left and truncator=FixedLength") {
                Then("it should become \"$expected\"") {
                    value.truncate(
                        length,
                        truncateFrom = TruncateFrom.Left,
                        truncator = Truncator.FixedLength
                    ) shouldBe expected
                }
            }
        }
    }
})

class TruncateFromLeftWithFixedNumberOfCharactersTests : BehaviorSpec({
    val data = listOf(
        TruncateData("", 10, ""),
        TruncateData("a", 1, "a"),
        TruncateData("Text with more characters than truncate length", 10, "…ate length"),
        TruncateData(
            "Text with number of characters equal to truncate length",
            47,
            "Text with number of characters equal to truncate length"
        ),
        TruncateData(
            "Text with less characters than truncate length",
            41,
            "Text with less characters than truncate length"
        ),
        TruncateData("Text with strange characters ^$(*^ and more ^$**)%  ", 10, "…rs ^$(*^ and more ^$**)%  ")
    )
    
    Given("a list of sentences") {
        forAll(data) { (value, length, expected) ->
            When("calling truncate with length=$length, truncateFrom=TruncateFrom.Left and truncator=FixedNumberOfCharacters") {
                Then("it should become \"$expected\"") {
                    value.truncate(
                        length,
                        truncateFrom = TruncateFrom.Left,
                        truncator = Truncator.FixedNumberOfCharacters
                    ) shouldBe expected
                }
            }
        }
    }
})

class TruncateFromLeftWithFixedNumberOfWordsTests : BehaviorSpec({
    val data = listOf(
        TruncateData("", 10, ""),
        TruncateData("a", 1, "a"),
        TruncateData("Text with more words than truncate length", 4, "…words than truncate length"),
        TruncateData(
            "Text with number of words equal to truncate length",
            9,
            "Text with number of words equal to truncate length"
        ),
        TruncateData("Text with less words than truncate length", 8, "Text with less words than truncate length"),
        TruncateData("Words are\nsplit\rby\twhitespace", 4, "…are\nsplit\rby\twhitespace"),
        TruncateData("Text with whitespace at the end  ", 4, "…whitespace at the end")
    )
    
    Given("a list of sentences") {
        forAll(data) { (value, length, expected) ->
            When("calling truncate with length=$length, truncateFrom=TruncateFrom.Left and truncator=FixedNumberOfWords") {
                Then("it should become \"$expected\"") {
                    value.truncate(
                        length,
                        truncateFrom = TruncateFrom.Left,
                        truncator = Truncator.FixedNumberOfWords
                    ) shouldBe expected
                }
            }
        }
    }
})

class TruncateFromLeftWithTruncationStringForFixedLengthTests : BehaviorSpec({
    val data = listOf(
        TruncateData("", 10, "", "..."),
        TruncateData("a", 1, "a", "..."),
        TruncateData("Text longer than truncate length", 10, "... length", "..."),
        TruncateData("Text with different truncation string", 10, "--- string", "---"),
        TruncateData(
            "Text with length equal to truncate length",
            41,
            "Text with length equal to truncate length",
            "..."
        ),
        TruncateData("Text smaller than truncate length", 34, "Text smaller than truncate length", "..."),
        TruncateData(
            "Text with delimiter length greater than truncate length truncates to fixed length without truncation string",
            2,
            "ng",
            "..."
        )
    )
    
    Given("a list of sentences") {
        forAll(data) { (value, length, expected, truncationString) ->
            When("calling truncate with length=$length, truncationString=$truncationString, truncateFrom=TruncateFrom.Left and truncator=FixedLength") {
                Then("it should become \"$expected\"") {
                    value.truncate(
                        length,
                        truncationString = truncationString,
                        truncateFrom = TruncateFrom.Left,
                        truncator = Truncator.FixedLength
                    ) shouldBe expected
                }
            }
        }
    }
})

class TruncateFromLeftWithTruncationStringForFixedNumberOfCharactersTests : BehaviorSpec({
    val data = listOf(
        TruncateData("", 10, "", "..."),
        TruncateData("a", 1, "a", "..."),
        TruncateData("Text with more characters than truncate length", 10, "...e length", "..."),
        TruncateData("Text with different truncation string", 10, "---n string", "---"),
        TruncateData(
            "Text with number of characters equal to truncate length",
            47,
            "Text with number of characters equal to truncate length",
            "..."
        ),
        TruncateData(
            "Text with less characters than truncate length",
            41,
            "Text with less characters than truncate length",
            "..."
        ),
        TruncateData(
            "Text with delimiter length greater than truncate length truncates to fixed number of characters without truncation string",
            2,
            "ng",
            "..."
        )
    )
    
    Given("a list of sentences") {
        forAll(data) { (value, length, expected, truncationString) ->
            When("calling truncate with length=$length, truncationString=$truncationString, truncateFrom=TruncateFrom.Left and truncator=FixedNumberOfCharacters") {
                Then("it should become \"$expected\"") {
                    value.truncate(
                        length,
                        truncationString = truncationString,
                        truncateFrom = TruncateFrom.Left,
                        truncator = Truncator.FixedNumberOfCharacters
                    ) shouldBe expected
                }
            }
        }
    }
})

class TruncateFromLeftWithTruncationStringForFixedNumberOfWordsTests : BehaviorSpec({
    val data = listOf(
        TruncateData("", 10, "", "..."),
        TruncateData("a", 1, "a", "..."),
        TruncateData("Text with more words than truncate length", 4, "...words than truncate length", "..."),
        TruncateData("Text with different truncation string", 4, "---with different truncation string", "---"),
        TruncateData(
            "Text with number of words equal to truncate length",
            9,
            "Text with number of words equal to truncate length",
            "..."
        ),
        TruncateData(
            "Text with less words than truncate length",
            8,
            "Text with less words than truncate length",
            "..."
        ),
        TruncateData("Words are\nsplit\rby\twhitespace", 4, "...are\nsplit\rby\twhitespace", "..."),
        TruncateData("Text with whitespace at the end  ", 4, "...whitespace at the end", "...")
    )
    
    Given("a list of sentences") {
        forAll(data) { (value, length, expected, truncationString) ->
            When("calling truncate with length=$length, truncationString=$truncationString, truncateFrom=TruncateFrom.Left and truncator=FixedNumberOfWords") {
                Then("it should become \"$expected\"") {
                    value.truncate(
                        length,
                        truncationString = truncationString,
                        truncateFrom = TruncateFrom.Left,
                        truncator = Truncator.FixedNumberOfWords
                    ) shouldBe expected
                }
            }
        }
    }
})

data class TruncateData(
    val value: String,
    val length: Int,
    val expected: String,
    val truncationString: String = "…"
) {
    override fun toString(): String = "\"$value\" and truncation string $truncationString"
}
     
