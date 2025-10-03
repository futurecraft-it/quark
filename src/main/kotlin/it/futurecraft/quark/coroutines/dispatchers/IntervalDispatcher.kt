package it.futurecraft.quark.coroutines.dispatchers

import it.futurecraft.quark.coroutines.elements.Timing
import it.futurecraft.quark.extensions.timing
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import org.bukkit.plugin.Plugin
import kotlin.coroutines.CoroutineContext

open class IntervalDispatcher(private val _plugin: Plugin) : CoroutineDispatcher() {
    override fun isDispatchNeeded(context: CoroutineContext): Boolean =
        _plugin.isEnabled && !_plugin.server.isPrimaryThread

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        if (!_plugin.isEnabled) return

        val (delay) = context[Timing] ?: 0L.timing
        val (period) = context[Timing] ?: 20L.timing

        _plugin.server.scheduler.runTaskTimer(_plugin, block, delay, period)
    }
}


