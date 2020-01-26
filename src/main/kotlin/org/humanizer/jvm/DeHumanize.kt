@file:JvmName("DeHumanize")

@file:Suppress("RedundantLambdaArrow")

package org.humanizer.jvm

fun String.dehumanize(): String = this
    .split(" ")
    .let { it.map { it -> it.capitalize() } }
    .joinToString("")