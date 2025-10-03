package it.futurecraft.quark

import it.futurecraft.quark.coroutines.ExceptionHandler
import it.futurecraft.quark.extensions.MinecraftDispatcher

import kotlinx.coroutines.*
import org.bukkit.plugin.java.JavaPlugin
import kotlin.coroutines.CoroutineContext

/**
 * Base class for all Quark plugins.
 *
 *     
 *     
 */
open class Quark : JavaPlugin(), IQuark {
    override suspend fun onLoadAsync() {}

    override suspend fun onEnableAsync() {}

    override suspend fun onDisableAsync() {}

    override fun onLoad() = runBlocking { onLoadAsync() }

    override fun onEnable() = runBlocking { onEnableAsync() }

    override fun onDisable() {
        runBlocking { onDisableAsync() }

        scope.coroutineContext.cancelChildren()
        scope.cancel()
    }

    override val scope: CoroutineScope =
        CoroutineScope(MinecraftDispatcher + SupervisorJob() + ExceptionHandler(this))

    override fun launch(
        ctx: CoroutineContext,
        start: CoroutineStart,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        if (!scope.isActive) return Job()

        return scope.launch(ctx, start, block)
    }
}