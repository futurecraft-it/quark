/*
 *     quark
 *     Copyright (C) 2025  FUTURECRAFT
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package it.futurecraft.quark.event

import it.futurecraft.quark.Quark
import it.futurecraft.quark.coroutines.dispatchers.MinecraftDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.plugin.EventExecutor
import org.bukkit.plugin.Plugin
import org.bukkit.event.Listener as BukkitListener

/**
 * Creates a new listener for the given event type.
 *
 * @param T The type of event to listen for.
 * @param priority The priority of the listener.
 * @param ignoreCancelled Whether to ignore cancelled events.
 * @param block The block to execute when the event is fired.
 */
inline fun <reified T : Event> listen(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline block: suspend T.() -> Unit
): Listener = object : Listener, EventExecutor {
    private lateinit var _plugin: Quark

    override fun register(plugin: Quark) {
        _plugin = plugin

        plugin.server.pluginManager.registerEvent(
            T::class.java,
            this,
            priority,
            this,
            plugin,
            ignoreCancelled
        )
    }

    override fun unregister() = HandlerList.unregisterAll(this)

    override fun execute(listener: BukkitListener, event: Event) {
        if (event is T) _plugin.schedule { event.block() }
    }
}

/**
 * A listener for a specific event type.
 * This interface extends Bukkit's Listener and provides methods to register and unregister the listener.
 */
interface Listener : BukkitListener {
    /**
     * Registers this listener for the given plugin.
     */
    fun register(plugin: Quark)

    /**
     * Unregisters this listener from all events.
     */
    fun unregister()
}
