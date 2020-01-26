@file:JvmName("InflectionHumanizer")
@file:Suppress("MapGetWithNotNullAssertionOperator")

package org.humanizer.jvm

import org.humanizer.jvm.exceptions.NoRuleFoundException
import java.util.regex.Pattern

fun String.camelize(): String {
    var previousWasUnderscore = false
    var t = ""
    
    this.decapitalize().forEach {
        if (it.toString() == "_") previousWasUnderscore = true
        if (Character.isLetterOrDigit(it) && previousWasUnderscore) {
            previousWasUnderscore = false
            t = "$t${it.toString().toUpperCase()}"
        } else {
            t = "$t$it"
        }
    }
    
    return t.replace("_", "")
}

fun String.pascalize(): String {
    var previousWasUnderscore = false
    var t = ""
    
    this.capitalize().forEach {
        if (it.toString() == "_") previousWasUnderscore = true
        if (Character.isLetterOrDigit(it) && previousWasUnderscore) {
            previousWasUnderscore = false
            t = "$t${it.toString().toUpperCase()}"
        } else {
            t = "$t$it"
        }
    }
    
    return t.replace("_", "")
}

fun String.underscore(): String {
    var previousWasCapital = false
    var t = ""
    
    this.decapitalize().forEach {
        if (Character.isUpperCase(it)) previousWasCapital = true
        if (Character.isLetterOrDigit(it) && previousWasCapital) {
            previousWasCapital = false
            t = "${t}_${it.toString().toLowerCase()}"
        } else t = "$t$it"
    }
    
    return t.replace(" ", "_")
}

fun String.titleize(): String {
    var previousWasWhitespace = false
    var t = ""
    
    this.replace("-", " ").replace("_", " ").capitalize().forEach {
        if (Character.isWhitespace(it) || !Character.isLetterOrDigit(it)) previousWasWhitespace = true
        if (Character.isLetterOrDigit(it) && previousWasWhitespace) {
            previousWasWhitespace = false
            t = "$t${it.toString().toUpperCase()}"
        } else {
            t = "$t$it"
        }
    }
    
    return t
}

fun String.dasherize(): String = this.replace("_", "-")

fun String.hyphenate(): String = this.replace("_", "-")

/*
The pluralize and singularize methods are based on the code found in the following places.

https://github.com/atteo/evo-inflector/blob/master/src/main/java/org/atteo/evo/inflector/English.java
http://www.java2s.com/Tutorial/Java/0040__Data-Type/Transformswordstosingularpluralhumanizedhumanreadableunderscorecamelcaseorordinalform.htm
https://github.com/rails/rails/blob/26698fb91d88dca0f860adcb80528d8d3f0f6285/activesupport/lib/active_support/inflector/inflections.rb

 */
@JvmOverloads
fun String.pluralize(plurality: Plurality = Plurality.Singular): String = when {
    plurality == Plurality.Plural -> this
    plurality == Plurality.Singular -> this.pluralizer()
    this.singularizer() != this
            && this.singularizer() + "s" != this
            && this.singularizer().pluralizer() == this
            && this.pluralizer() != this -> this
    else -> this.pluralizer()
}

@JvmOverloads
fun String.singularize(plurality: Plurality = Plurality.Plural): String = when {
    plurality == Plurality.Singular -> this
    plurality == Plurality.Plural -> this.singularizer()
    this.pluralizer() != this
            && this + "s" != this.pluralizer()
            && this.pluralizer().singularize() == this
            && this.singularizer() != this -> this
    else -> this.singularize()
}

private fun String.pluralizer(): String = when {
    unCountable.contains(this) -> this
    else -> {
        val rule = pluralizationRules.entries.last {
            Pattern.compile(it.key, Pattern.CASE_INSENSITIVE).matcher(this).find()
        }
        
        var found = Pattern.compile(rule.key, Pattern.CASE_INSENSITIVE).matcher(this).replaceAll(rule.value)
        
        val endsWith = exceptions.entries.firstOrNull { this.endsWith(it.key) }
        if (endsWith != null) found = this.replace(endsWith.key, endsWith.value)
        
        val exception = exceptions.entries.firstOrNull { this == it.key }
        if (exception != null) found = exception.value
        
        found
    }
}

private fun String.singularizer(): String {
    if (unCountable.contains(this)) return this
    if (this in exceptions) return exceptions[this]!!
    
    val endsWith = exceptions.entries.firstOrNull { this.endsWith(it.value) }
    
    if (endsWith != null) return this.replace(endsWith.value, endsWith.key)
    
    try {
        if (singularizationRules.count {
                Pattern.compile(it.key, Pattern.CASE_INSENSITIVE).matcher(this).find()
            } == 0) return this
        val rule = singularizationRules.entries.last {
            Pattern.compile(it.key, Pattern.CASE_INSENSITIVE).matcher(this).find()
        }
        return Pattern.compile(rule.key, Pattern.CASE_INSENSITIVE).matcher(this).replaceAll(rule.value)
    } catch (ex: IllegalArgumentException) {
        NoRuleFoundException("singularize")
    }
    
    return this
}


val unCountable: Set<String> = setOf(
    "equipment", "information", "rice", "money",
    "species", "series", "fish", "sheep", "aircraft", "bison", "flounder", "pliers", "bream",
    "gallows", "proceedings", "breeches", "graffiti", "rabies",
    "britches", "headquarters", "salmon", "carp", "herpes",
    "scissors", "chassis", "high-jinks", "sea-bass", "clippers",
    "homework", "cod", "innings", "shears",
    "contretemps", "jackanapes", "corps", "mackerel",
    "swine", "debris", "measles", "trout", "diabetes", "mews",
    "tuna", "djinn", "mumps", "whiting", "eland", "news",
    "wildebeest", "elk", "pincers", "sugar"
)

val exceptions: Map<String, String> = mapOf(
    "person" to "people",
    "man" to "men",
    "goose" to "geese",
    "child" to "children",
    "sex" to "sexes",
    "move" to "moves",
    "stadium" to "stadiums",
    "deer" to "deer",
    "codex" to "codices",
    "murex" to "murices",
    "silex" to "silices",
    "radix" to "radices",
    "helix" to "helices",
    "alumna" to "alumnae",
    "alga" to "algae",
    "vertebra" to "vertebrae",
    "persona" to "personae",
    "stamen" to "stamina",
    "foramen" to "foramina",
    "lumen" to "lumina",
    "afreet" to "afreeti",
    "afrit" to "afriti",
    "efreet" to "efreeti",
    "cherub" to "cherubim",
    "goy" to "goyim",
    "human" to "humans",
    "lumen" to "lumina",
    "seraph" to "seraphim",
    "Alabaman" to "Alabamans",
    "Bahaman" to "Bahamans",
    "Burman" to "Burmans",
    "German" to "Germans",
    "Hiroshiman" to "Hiroshimans",
    "Liman" to "Limans",
    "Nakayaman" to "Nakayamans",
    "Oklahoman" to "Oklahomans",
    "Panaman" to "Panamans",
    "Selman" to "Selmans",
    "Sonaman" to "Sonamans",
    "Tacoman" to "Tacomans",
    "Yakiman" to "Yakimans",
    "Yokohaman" to "Yokohamans",
    "Yuman" to "Yumans", "criterion" to "criteria",
    "perihelion" to "perihelia",
    "aphelion" to "aphelia",
    "phenomenon" to "phenomena",
    "prolegomenon" to "prolegomena",
    "noumenon" to "noumena",
    "organon" to "organa",
    "asyndeton" to "asyndeta",
    "hyperbaton" to "hyperbata"
)

val pluralizationRules: Map<String, String> = mapOf(
    "$" to "s",
    "s$" to "s",
    "(ax|test)is$" to "$1es",
    "us$" to "i",
    "(octop|vir)us$" to "$1i",
    "(octop|vir)i$" to "$1i",
    "(alias|status)$" to "$1es",
    "(bu)s$" to "$1ses",
    "(buffal|tomat)o$" to "$1oes",
    "([ti])um$" to "$1a",
    "([ti])a$" to "$1a",
    "sis$" to "ses",
    "(,:([^f])fe|([lr])f)$" to "$1$2ves",
    "(hive)$" to "$1s",
    "([^aeiouy]|qu)y$" to "$1ies",
    "(x|ch|ss|sh)$" to "$1es",
    "(matr|vert|ind)ix|ex$" to "$1ices",
    "([m|l])ouse$" to "$1ice",
    "([m|l])ice$" to "$1ice",
    "^(ox)$" to "$1en",
    "(quiz)$" to "$1zes",
    "f$" to "ves",
    "fe$" to "ves",
    "um$" to "a",
    "on$" to "a"
)

val singularizationRules: Map<String, String> = mapOf(
    "s$" to "",
    "(s|si|u)s$" to "$1s",
    "(n)ews$" to "$1ews",
    "([ti])a$" to "$1um",
    "((a)naly|(b)a|(d)iagno|(p)arenthe|(p)rogno|(s)ynop|(t)he)ses$" to "$1$2sis",
    "(^analy)ses$" to "$1sis",
    "(^analy)sis$" to "$1sis",
    "([^f])ves$" to "$1fe",
    "(hive)s$" to "$1",
    "(tive)s$" to "$1",
    "([lr])ves$" to "$1f",
    "([^aeiouy]|qu)ies$" to "$1y",
    "(s)eries$" to "$1eries",
    "(m)ovies$" to "$1ovie",
    "(x|ch|ss|sh)es$" to "$1",
    "([m|l])ice$" to "$1ouse",
    "(bus)es$" to "$1",
    "(o)es$" to "$1",
    "(shoe)s$" to "$1",
    "(cris|ax|test)is$" to "$1is",
    "(cris|ax|test)es$" to "$1is",
    "(octop|vir)i$" to "$1us",
    "(octop|vir)us$" to "$1us",
    "(alias|status)es$" to "$1",
    "(alias|status)$" to "$1",
    "^(ox)en" to "$1",
    "(vert|ind)ices$" to "$1ex",
    "(matr)ices$" to "$1ix",
    "(quiz)zes$" to "$1",
    "a$" to "um",
    "i$" to "us",
    "ae$" to "a"
)

enum class Plurality {
    Singular,
    Plural,
    CouldBeEither
}