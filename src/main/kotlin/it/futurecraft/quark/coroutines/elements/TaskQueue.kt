/*
 *     quark
 *     Copyright (C) 2026  FUTURECRAFT
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

package it.futurecraft.quark.coroutines.elements

import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

class TaskQueue : AbstractCoroutineContextElement(TaskQueue), Runnable {
    companion object Key : CoroutineContext.Key<TaskQueue>

    private val _queue = ConcurrentLinkedQueue<Runnable>()

    fun add(runnable: Runnable) {
        _queue.add(runnable)
    }

    override fun run() {
        if (_queue.isEmpty()) return

        _queue.poll().run()
    }
}