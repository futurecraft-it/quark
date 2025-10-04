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

package it.futurecraft.quark.utils

/**
 * Utility object to manage server environment.
 */
object Environment {
    private val _props by lazy { System.getProperties() }

    /**
     * The current environment type, determined by the "environment" system property.
     */
    val environment: Type =
        Type.from(_props.getProperty("environment", "production"))

    /**
     * Indicates if the current environment is development.
     */
    val development: Boolean
        get() = environment == Type.DEVELOPMENT

    /**
     * Indicates if the current environment is production.
     */
    val production: Boolean
        get() = environment == Type.PRODUCTION

    /**
     * The server software type.
     */
    val software: MinecraftSoftware by lazy {
        MinecraftSoftware.get()
    }

    /**
     * Enumeration of possible environment types.
     * - DEVELOPMENT: Represents a development environment.
     * - PRODUCTION: Represents a production environment.
     */
    enum class Type {
        DEVELOPMENT,
        PRODUCTION;

        companion object {
            fun from(value: String): Type = when(value.lowercase()) {
                "development" -> DEVELOPMENT
                else -> PRODUCTION
            }
        }
    }
}