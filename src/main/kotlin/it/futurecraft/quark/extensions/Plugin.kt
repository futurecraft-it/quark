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

package it.futurecraft.quark.extensions

import it.futurecraft.quark.Quark
import it.futurecraft.quark.coroutines.dispatchers.DelayedDispatcher
import it.futurecraft.quark.coroutines.dispatchers.IntervalDispatcher
import it.futurecraft.quark.coroutines.dispatchers.MinecraftDispatcher
import it.futurecraft.quark.utils.MinecraftVersion
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.plugin.Plugin
import it.futurecraft.quark.event.listen as _listen

val Plugin.ServerVersion: MinecraftVersion
    get() {
        val name = server.javaClass.getPackage().name.split(".").last()
        return MinecraftVersion.fromNMS(name)
    }

val Plugin.DelayedDispatcher: DelayedDispatcher
    get() = DelayedDispatcher(this)

val Plugin.IntervalDispatcher: IntervalDispatcher
    get() = IntervalDispatcher(this)

val Plugin.MinecraftDispatcher: MinecraftDispatcher
    get() = MinecraftDispatcher(this)

/**
 * Creates a new listener for the given event type and registers it to this plugin.
 *
 * @param T The type of event to listen for.
 * @param priority The priority of the listener.
 * @param ignoreCancelled Whether to ignore cancelled events.
 * @param block The block to execute when the event is fired.
 */
inline fun <reified T : Event> Quark.listen(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline block: suspend T.() -> Unit
) = _listen<T>(priority, ignoreCancelled, block).also { it.register(this) }
