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

package it.futurecraft.quark.coroutines.dispatchers

import it.futurecraft.quark.coroutines.elements.TaskQueue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import org.bukkit.plugin.Plugin
import kotlin.coroutines.CoroutineContext

open class MinecraftDispatcher(private val _plugin: Plugin) : CoroutineDispatcher() {
    override fun isDispatchNeeded(context: CoroutineContext): Boolean =
        _plugin.isEnabled && !_plugin.server.isPrimaryThread

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        if (_plugin.isEnabled) return

        val queue = context[TaskQueue]

        if (queue == null) {
            _plugin.server.scheduler.runTask(_plugin, block)
        } else {
            queue.add(block)
            _plugin.server.scheduler.runTask(_plugin, queue)
        }
    }
}
