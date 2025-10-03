package it.futurecraft.quark.extensions

import it.futurecraft.quark.coroutines.elements.Timing
import java.math.RoundingMode

/**
 * Converts milliseconds to Minecraft ticks.
 */
val Int.ticks: Long
    get() = this / 50L

/**
 * Create a [Timing] instance from a [Long] value representing milliseconds or ticks.
 */
val Long.timing
    get() = Timing(this)

/**
 * Whether the number is positive.
 */
val Number.positive: Boolean
    get() = toDouble() > .0

/**
 * Whether the number is negative.
 */
val Number.negative: Boolean
    get() = toDouble() < .0

/**
 * Whether the number positive or zero.
 */
val Number.nonNegative: Boolean
    get() = toDouble() >= .0

/**
 * Whether the number is negative or zero.
 */
val Number.nonPositive: Boolean
    get() = toDouble() <= .0

/**
 * Whether the number is an integer (no decimal part).
 * This does not check the type of the number, but its value.
 */
val Number.integer
    get() = toDouble() % 1 == 0.0

/**
 * Whether the number is a whole number (integer and non-negative).
 */
val Number.whole: Boolean
    get() = integer && nonNegative

/**
 * Rounds the number to the specified number of decimal places using half-up rounding.
 *
 * @param places The number of decimal places to round to.
 * @return The rounded number as a Double.
 */
infix fun Number.round(places: Int): Double = toDouble()
    .toBigDecimal()
    .setScale(places, RoundingMode.HALF_UP)
    .toDouble()
