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