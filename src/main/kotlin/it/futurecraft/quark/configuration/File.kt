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

/**
 * Represents a configuration file with a specific format and schema.
 * @param S The schema type for the configuration file.
 * @param name The name of the configuration file (without extension).
 * @param basePath The base path where the configuration file is located.
 * @param format The format of the configuration file (e.g., JSON, HOCON).
 * @param serializer The serializer for the schema type.
 */
class File<S : File.Schema>(
    val name: String,
    val basePath: Path,
    val format: Format,
    val serializer: KSerializer<S>,
) {
    companion object {
        /**
         * Creates a JSON configuration file instance.
         *
         * @param name The name of the configuration file (without extension).
         * @param basePath The base path where the configuration file is located. Defaults to the current directory.
         * @param serializer The serializer for the schema type.
         * @return A [File] instance representing a JSON configuration file.
         */
        fun <S : Schema> json(name: String, basePath: Path = Path.of(""), serializer: KSerializer<S>) =
            File(name, basePath, Format.JSON, serializer)

        /**
         * Creates a HOCON configuration file instance.
         *
         * @param name The name of the configuration file (without extension).
         * @param basePath The base path where the configuration file is located. Defaults to the current directory.
         * @param serializer The serializer for the schema type.
         * @return A [File] instance representing a HOCON configuration file.
         */
        fun <S : Schema> hocon(name: String, basePath: Path = Path.of(""), serializer: KSerializer<S>) =
            File(name, basePath, Format.HOCON, serializer)
    }

    /**
     * The full name of the configuration file, including its format extension.
     */
    val fullName: String
        get() = "$name$format"

    /**
     * The full path to the configuration file.
     */
    val path: Path
        get() = basePath / fullName

    /**
     * Enum representing the supported file formats and their associated extensions.
     *
     * You can implement you very own formatter by implementing the [Formatter] interface and creating a new instance of this class.
     * Extending the companion object is also possible to add your custom formats as static members.
     *
     * @param extension The file extension for the format (e.g., "json", "conf").
     * @param formatter The formatter used to read/write the file in this format.
     */
    class Format(
        val extension: String,
        val formatter: Formatter
    ) {
        companion object {
            /**
             * JSON file format with ".json" extension.
             */
            val JSON = Format("json", JsonFormatter)

            /**
             * HOCON file format with ".conf" extension.
             */
            val HOCON = Format("conf", HoconFormatter)
        }

        override fun toString(): String = ".$extension"
    }

    /**
     * Marker interface for schema types used in configuration files.
     */
    interface Schema

    /**
     * Represents a key within a configuration file schema.
     *
     * @param S The schema type associated with the key.
     * @param file The configuration file to which this key belongs.
     */
    abstract class Key<S : Schema>(val file: File<S>) {
        abstract val default: S
    }
}