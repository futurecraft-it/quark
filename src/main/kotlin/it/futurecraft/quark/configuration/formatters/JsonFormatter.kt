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

package it.futurecraft.quark.configuration.formatters

import it.futurecraft.quark.configuration.Formatter
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialFormat
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File

@OptIn(ExperimentalSerializationApi::class)
object JsonFormatter : Formatter() {
    override val format: SerialFormat
        get() = _json

    private val _json = Json { prettyPrint = true }

    override fun <T> serialize(data: T, serializer: SerializationStrategy<T>): String =
        _json.encodeToString(serializer, data)

    override fun <T> deserialize(file: File, deserializer: DeserializationStrategy<T>): T = file.run {
        _json.decodeFromStream(deserializer, inputStream())
    }
}