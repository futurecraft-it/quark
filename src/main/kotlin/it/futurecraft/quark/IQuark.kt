package it.futurecraft.quark

import it.futurecraft.quark.extensions.DelayedDispatcher
import it.futurecraft.quark.extensions.IntervalDispatcher
import it.futurecraft.quark.extensions.MinecraftDispatcher
import it.futurecraft.quark.extensions.timing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import org.bukkit.plugin.Plugin
import kotlin.coroutines.CoroutineContext

/**
 * An interface representing a Quark.
 *
 * A quark is a plugin with built-in coroutine support & more.
 */
interface IQuark : Plugin {
    /**
     * The coroutine scope associated with this quark.
     */
    val scope: CoroutineScope

    /**
     * Called when the plugin is being loaded, in a coroutine context.
     */
    suspend fun onLoadAsync()

    /**
     * Called when the plugin is being enabled, in a coroutine context.
     */
    suspend fun onEnableAsync()

    /**
     * Called when the plugin is being disabled, in a coroutine context.
     */
    suspend fun onDisableAsync()

    /**
     * Launches a new coroutine in the plugin's scope.
     *
     * @param ctx The coroutine context to use. Defaults to [MinecraftDispatcher].
     * @param start The coroutine start option. Defaults to [CoroutineStart.DEFAULT].
     * @param block The suspend function to execute in the coroutine.
     * @return A [Job] representing the launched coroutine.
     */
    fun launch(
        ctx: CoroutineContext = MinecraftDispatcher,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job

    /**
     * Schedules a coroutine to run after a specified delay on server main thread.
     *
     * @param delay The delay in ticks before executing the block.
     * @param block The suspend function to execute after the delay.
     * @return A [Job] representing the scheduled coroutine.
     */
    fun delay(delay: Long, block: suspend CoroutineScope.() -> Unit): Job =
        launch(ctx = DelayedDispatcher + delay.timing, block = block)


    /**
     * Schedules a coroutine to run repeatedly at a specified interval on server main thread.
     *
     * @param delay The initial delay in ticks before the first execution of the block. Defaults to 0.
     * @param period The interval in ticks between subsequent executions of the block.
     * @param block The suspend function to execute at each interval.
     */
    fun repeat(delay: Long = 0, period: Long, block: suspend CoroutineScope.() -> Unit): Job =
        launch(ctx = IntervalDispatcher + delay.timing + period.timing, block = block)
}