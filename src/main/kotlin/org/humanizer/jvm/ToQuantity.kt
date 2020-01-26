@file:JvmName("QuanityHumanizer")

package org.humanizer.jvm

@JvmOverloads
fun String.toQuantity(quantity: Int, showQuantityAs: ShowQuantityAs = ShowQuantityAs.Numeric): String =
    this.toQuantity(quantity.toLong(), showQuantityAs)

@JvmOverloads
fun String.toQuantity(quantity: Long, showQuantityAs: ShowQuantityAs = ShowQuantityAs.Numeric): String =
    when (showQuantityAs) {
        ShowQuantityAs.Numeric -> {
            val formattedQuantity = quantity.toString()
            when (quantity) {
                1L, -1L -> "$formattedQuantity ${this.singularize(Plurality.CouldBeEither)}"
                else -> "$formattedQuantity ${this.pluralize(Plurality.CouldBeEither)}"
            }
        }
        ShowQuantityAs.None -> {
            when (quantity) {
                1L, -1L -> this.singularize(Plurality.CouldBeEither)
                else -> this.pluralize(Plurality.CouldBeEither)
            }
        }
        ShowQuantityAs.Words -> {
            when (quantity) {
                1L, -1L -> "${quantity.toWords()} ${this.singularize(Plurality.CouldBeEither)}"
                else -> "${quantity.toWords()} ${this.pluralize(Plurality.CouldBeEither)}"
            }
        }
    }

@JvmOverloads
fun Int.toQuantity(unit: String, showQuantityAs: ShowQuantityAs = ShowQuantityAs.Numeric): String =
    unit.toQuantity(this, showQuantityAs)

@JvmOverloads
fun Long.toQuantity(unit: String, showQuantityAs: ShowQuantityAs = ShowQuantityAs.Numeric): String =
    unit.toQuantity(this, showQuantityAs)

enum class ShowQuantityAs {
    None,
    Numeric,
    Words
}
