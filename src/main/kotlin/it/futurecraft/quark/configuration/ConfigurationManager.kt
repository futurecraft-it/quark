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

package it.futurecraft.quark.configuration

import it.futurecraft.quark.Quark
import it.futurecraft.quark.extensions.exists
import it.futurecraft.quark.extensions.file
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import kotlin.io.path.div

class ConfigurationManager(private val _plugin: Quark) {
    private val _cache = ConcurrentHashMap<File.Key<*>, File.Schema>()

    /**
     * Saves the file data to disk if it has changed.
     *
     * Though this function is asynchronous, it is recommended to call it only when necessary,
     * since it involves disk I/O and configuration files may be large (especially messages files).
     *
     *
     * @param key The file key.
     * @param data The file data.
     * @return true only if saving was necessary.
     */
    suspend operator fun <S : File.Schema> set(key: File.Key<S>, data: S): Boolean = withContext(Dispatchers.IO) {
        val cached = _cache[key]

        if (cached != null && cached == data) return@withContext false

        _cache[key] = data

        val path = _plugin.dataPath / key.file.path
        val serializer = key.file.serializer

        val formatter = key.file.format.formatter

        path.file.run {
            if (!exists) parentFile.mkdirs()

            val content = formatter.serialize(data, serializer)
            writeText(content)
        }

        return@withContext true
    }

    /**
     * Loads the file data from disk or cache.
     *
     * @param key The file key.
     * @return The file data.
     */
    suspend operator fun <S : File.Schema> get(key: File.Key<S>): S = withContext(Dispatchers.IO) {
        val schema = _cache.computeIfAbsent(key) { k ->
            val path = _plugin.dataPath / k.file.path
            val serializer = k.file.serializer

            val formatter = k.file.format.formatter

            path.file.run { formatter.deserialize(this, serializer) }
        }

        schema as S
    }


    /**
     * Clears the configuration cache.
     *
     * This will force all configurations to be reloaded from disk on the next access.
     */
    fun clear() = _cache.clear()
}