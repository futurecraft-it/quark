package it.futurecraft.quark.extensions

import it.futurecraft.quark.coroutines.dispatchers.DelayedDispatcher
import it.futurecraft.quark.coroutines.dispatchers.IntervalDispatcher
import it.futurecraft.quark.coroutines.dispatchers.MinecraftDispatcher
import it.futurecraft.quark.utils.MinecraftVersion
import org.bukkit.plugin.Plugin

val Plugin.ServerVersion: MinecraftVersion
    get() {
        val name = server.javaClass.getPackage().name.split(".").last()
        return MinecraftVersion.fromNMS(name)
    }

val Plugin.DelayedDispatcher: DelayedDispatcher
    get() = DelayedDispatcher(this)

val Plugin.IntervalDispatcher: IntervalDispatcher
    get() = IntervalDispatcher(this)

val Plugin.MinecraftDispatcher: MinecraftDispatcher
    get() = MinecraftDispatcher(this)
