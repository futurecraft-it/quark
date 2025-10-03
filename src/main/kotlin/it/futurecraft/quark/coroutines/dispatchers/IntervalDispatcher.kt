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


