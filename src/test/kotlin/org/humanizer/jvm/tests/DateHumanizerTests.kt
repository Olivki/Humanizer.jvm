package org.humanizer.jvm.tests

import io.kotlintest.forAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import org.humanizer.jvm.humanize
import java.util.*

class DateHumanizerTests : BehaviorSpec({
    data class ParamClass(
        val timeUnit: Int,
        val input: Int,
        val expected: String,
        val dateToUse: Date = GregorianCalendar(2014, Calendar.JANUARY, 5).time
    ) {
        override fun toString(): String = "a date and $input"
    }
    
    val data = listOf(
        ParamClass(Calendar.SECOND, -1, "one second ago"),
        ParamClass(Calendar.SECOND, -10, "10 seconds ago"),
        ParamClass(Calendar.SECOND, -59, "59 seconds ago"),
        ParamClass(Calendar.SECOND, -60, "a minute ago"),
        ParamClass(Calendar.SECOND, 1, "one second from now"),
        ParamClass(Calendar.SECOND, 10, "10 seconds from now"),
        ParamClass(Calendar.SECOND, 59, "59 seconds from now"),
        ParamClass(Calendar.SECOND, 60, "a minute from now"),
        ParamClass(Calendar.MINUTE, -1, "a minute ago"),
        ParamClass(Calendar.MINUTE, -10, "10 minutes ago"),
        ParamClass(Calendar.MINUTE, -59, "59 minutes ago"),
        ParamClass(Calendar.MINUTE, -60, "an hour ago"),
        ParamClass(Calendar.MINUTE, -119, "an hour ago"),
        ParamClass(Calendar.MINUTE, -120, "2 hours ago"),
        ParamClass(Calendar.MINUTE, 1, "a minute from now"),
        ParamClass(Calendar.MINUTE, 10, "10 minutes from now"),
        ParamClass(Calendar.MINUTE, 59, "59 minutes from now"),
        ParamClass(Calendar.MINUTE, 60, "an hour from now"),
        ParamClass(Calendar.MINUTE, 119, "an hour from now"),
        ParamClass(Calendar.MINUTE, 120, "2 hours from now"),
        ParamClass(Calendar.HOUR, -1, "an hour ago"),
        ParamClass(Calendar.HOUR, -10, "10 hours ago"),
        ParamClass(Calendar.HOUR, -23, "23 hours ago"),
        ParamClass(Calendar.HOUR, -24, "yesterday"),
        ParamClass(Calendar.HOUR, 1, "an hour from now"),
        ParamClass(Calendar.HOUR, 10, "10 hours from now"),
        ParamClass(Calendar.HOUR, 23, "23 hours from now"),
        ParamClass(Calendar.HOUR, 24, "tomorrow"),
        ParamClass(Calendar.DATE, -1, "yesterday"),
        ParamClass(Calendar.DATE, -10, "10 days ago"),
        ParamClass(Calendar.DATE, -28, "28 days ago"),
        ParamClass(Calendar.DATE, -32, "one month ago"),
        ParamClass(Calendar.DATE, 1, "tomorrow"),
        ParamClass(Calendar.DATE, 10, "10 days from now"),
        ParamClass(Calendar.DATE, 28, "28 days from now"),
        ParamClass(Calendar.DATE, 29, "one month from now", GregorianCalendar(2014, Calendar.FEBRUARY, 5).time),
        ParamClass(Calendar.DATE, 32, "one month from now"),
        ParamClass(Calendar.MONTH, -1, "one month ago"),
        ParamClass(Calendar.MONTH, -10, "10 months ago"),
        ParamClass(Calendar.MONTH, -11, "11 months ago"),
        ParamClass(Calendar.MONTH, -12, "one year ago"),
        ParamClass(Calendar.MONTH, 1, "one month from now"),
        ParamClass(Calendar.MONTH, 10, "10 months from now"),
        ParamClass(Calendar.MONTH, 11, "11 months from now"),
        ParamClass(Calendar.MONTH, 12, "one year from now"),
        ParamClass(Calendar.YEAR, -1, "one year ago"),
        ParamClass(Calendar.YEAR, -2, "2 years ago"),
        ParamClass(Calendar.YEAR, 1, "one year from now"),
        ParamClass(Calendar.YEAR, 2, "2 years from now"),
        ParamClass(Calendar.SECOND, 0, "now"),
        ParamClass(Calendar.MINUTE, 0, "now"),
        ParamClass(Calendar.HOUR, 0, "now"),
        ParamClass(Calendar.DAY_OF_YEAR, 0, "now"),
        ParamClass(Calendar.MONTH, 0, "now"),
        ParamClass(Calendar.YEAR, 0, "now")
    )
    
    Given("a list of dates") {
        forAll(data) { (timeUnit, input, expected, dateToUse) ->
            When("calling humanize with \"$input\" for timeunit $timeUnit") {
                val cal = GregorianCalendar()
                cal.time = dateToUse
                cal.add(timeUnit, input)
                
                Then("it should be \"$expected\"") {
                    cal.time.humanize(dateToUse) shouldBe expected
                }
            }
        }
    }
    
    Given("a date that is 1 year in the future") {
        When("calling humanize against current date") {
            val cal = GregorianCalendar()
            cal.add(Calendar.MONTH, 13)
            Then("it should be one year from now") {
                cal.time.humanize() shouldBe "one year from now"
            }
        }
    }
})