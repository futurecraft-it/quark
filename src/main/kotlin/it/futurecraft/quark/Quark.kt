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