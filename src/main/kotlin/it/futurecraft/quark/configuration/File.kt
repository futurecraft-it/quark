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

import it.futurecraft.quark.configuration.formatters.HoconFormatter
import it.futurecraft.quark.configuration.formatters.JsonFormatter
import kotlinx.serialization.KSerializer
import java.nio.file.Path
import kotlin.io.path.div

class File<S : File.Schema>(
    val name: String,
    val basePath: Path,
    val format: Format,
    val serializer: KSerializer<S>,
) {
    companion object {
        fun <S : Schema> json(name: String, basePath: Path, serializer: KSerializer<S>) =
            File(name, basePath, Format.JSON, serializer)

        fun <S : Schema> hocon(name: String, basePath: Path, serializer: KSerializer<S>) =
            File(name, basePath, Format.HOCON, serializer)
    }

    val fullName: String
        get() = "$name$format"

    val path: Path
        get() = basePath / fullName

    fun formatter() = when (format) {
        Format.JSON -> JsonFormatter
        Format.HOCON -> HoconFormatter
    }

    enum class Format(val extension: String) {
        JSON("json"),
        HOCON("conf");

        override fun toString(): String = ".$extension"
    }

    interface Schema

    abstract class Key<S : Schema>(val file: File<S>)
}