package it.futurecraft.quark.coroutines.elements

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

data class Timing(val timing: Long) : AbstractCoroutineContextElement(Timing) {
    companion object Key : CoroutineContext.Key<Timing>
}