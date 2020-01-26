package org.humanizer.jvm.exceptions

// TODO: Replace this with a range.
class ArgumentOutOfRangeException(argument: Int, minValue: Int, maxValue: Int) :
    Exception("The argument $argument is out of range, the range is $minValue to $maxValue.")

