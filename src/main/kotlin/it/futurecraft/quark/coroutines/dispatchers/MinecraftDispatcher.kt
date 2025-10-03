package it.futurecraft.quark.coroutines.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import org.bukkit.plugin.Plugin
import kotlin.coroutines.CoroutineContext

open class MinecraftDispatcher(private val _plugin: Plugin) : CoroutineDispatcher() {
    override fun isDispatchNeeded(context: CoroutineContext): Boolean =
        _plugin.isEnabled && !_plugin.server.isPrimaryThread

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        if (_plugin.isEnabled) return

        _plugin.server.scheduler.runTask(_plugin, block)
    }
}
