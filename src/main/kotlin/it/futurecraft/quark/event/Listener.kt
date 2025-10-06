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
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.EventExecutor

/**
 * Creates a new listener for the given event type.
 *
 * @param T The type of event to listen for.
 * @param plugin The plugin instance.
 * @param priority The priority of the listener.
 * @param ignoreCancelled Whether to ignore cancelled events.
 * @param block The block to execute when the event is fired.
 */
inline fun <reified T : Event> listen(
    plugin: Quark,
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline block: T.() -> Unit
): Listener = object : Listener, EventExecutor {
    init {
        plugin.server.pluginManager.registerEvent(
            T::class.java,
            this,
            priority,
            this,
            plugin,
            ignoreCancelled
        )
    }

    override fun execute(listener: Listener, event: Event) {
        if (event is T) event.block()
    }
}

/**
 * Creates a new listener for the given event type.
 *
 * @param T The type of event to listen for.
 * @param priority The priority of the listener.
 * @param ignoreCancelled Whether to ignore cancelled events.
 * @param block The block to execute when the event is fired.
 */
inline fun <reified T : Event> Quark.listen(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline block: T.() -> Unit
) = listen(this, priority, ignoreCancelled, block)
