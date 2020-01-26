package org.humanizer.jvm.exceptions

class NoRuleFoundException(inflection: String) :
    Exception("Can't $inflection this word, could not find a rule to match.")
