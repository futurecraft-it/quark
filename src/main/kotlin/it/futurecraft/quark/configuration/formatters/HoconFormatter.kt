package it.futurecraft.quark.configuration.formatters

import com.typesafe.config.ConfigFactory
import it.futurecraft.quark.configuration.Formatter
import kotlinx.serialization.*
import kotlinx.serialization.hocon.Hocon
import java.io.File

@OptIn(ExperimentalSerializationApi::class)
object HoconFormatter : Formatter() {
    override val format: SerialFormat
        get() = _hocon

    private val _hocon = Hocon { encodeDefaults = true }

    override fun <T> serialize(data: T, serializer: SerializationStrategy<T>): String {
        val config = _hocon.encodeToConfig(serializer, data)
        return config.root().render()
    }


    override fun <T> deserialize(file: File, deserializer: DeserializationStrategy<T>): T = file.run {
        val config = ConfigFactory.parseFile(this)
        _hocon.decodeFromConfig(deserializer, config)
    }
}