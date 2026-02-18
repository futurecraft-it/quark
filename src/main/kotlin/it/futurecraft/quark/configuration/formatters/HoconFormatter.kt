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

import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigRenderOptions
import it.futurecraft.quark.configuration.Formatter
import kotlinx.serialization.*
import kotlinx.serialization.hocon.Hocon
import java.io.File

@OptIn(ExperimentalSerializationApi::class)
/**
 * Formatter implementation for HOCON (Human-Optimized Config Object Notation) format.
 */
object HoconFormatter : Formatter() {
    override val format: SerialFormat
        get() = _hocon

    private val _hocon = Hocon { encodeDefaults = true }

    override fun <T> serialize(data: T, serializer: SerializationStrategy<T>): String {
        val config = _hocon.encodeToConfig(serializer, data)
        return config.root().render(ConfigRenderOptions.concise().setFormatted(true).setJson(false))
    }


    override fun <T> deserialize(file: File, deserializer: DeserializationStrategy<T>): T = file.run {
        val config = ConfigFactory.parseFile(this)
        _hocon.decodeFromConfig(deserializer, config)
    }
}