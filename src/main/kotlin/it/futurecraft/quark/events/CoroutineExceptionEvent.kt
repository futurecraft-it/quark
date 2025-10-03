package it.futurecraft.quark.events

import it.futurecraft.quark.Quark
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.server.ServerEvent

class CoroutineExceptionEvent(
    val plugin: Quark,
    val exception: Throwable
) : ServerEvent(false), Cancellable {
    companion object {
        private val HANDLERS = HandlerList()

        @JvmStatic
        fun getHandlerList() = HANDLERS
    }

    private var _cancelled = false

    override fun isCancelled(): Boolean = _cancelled

    override fun setCancelled(cancel: Boolean) {
        _cancelled = cancel
    }

    override fun getHandlers() = HANDLERS
}