package it.futurecraft.quark.coroutines

import it.futurecraft.quark.Quark
import it.futurecraft.quark.events.CoroutineExceptionEvent
import kotlinx.coroutines.CoroutineExceptionHandler

fun ExceptionHandler(plugin: Quark) = CoroutineExceptionHandler { _, exception ->
    val event = CoroutineExceptionEvent(plugin, exception)

    if (plugin.isEnabled) {
        plugin.server.scheduler.runTask(plugin, Runnable { event.callEvent() })
    }
}