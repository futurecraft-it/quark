package it.futurecraft.quark.extensions

import java.io.File
import java.nio.file.Path

val Path.file: File
    get() = toFile()

val File.exists: Boolean
    get() = exists()
