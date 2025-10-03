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

enum class MinecraftVersion {
    v1_8,
    v1_9,
    v1_10,
    v1_11,
    v1_12,
    v1_13,
    v1_14,
    v1_15,
    v1_16,
    v1_17,
    v1_18,
    v1_19,
    v1_20,
    v1_21,
    UNKNOWN;

    companion object {
        fun fromNMS(version: String): MinecraftVersion {
            return when {
                version.startsWith("v1_8") -> v1_8
                version.startsWith("v1_9") -> v1_9
                version.startsWith("v1_10") -> v1_10
                version.startsWith("v1_11") -> v1_11
                version.startsWith("v1_12") -> v1_12
                version.startsWith("v1_13") -> v1_13
                version.startsWith("v1_14") -> v1_14
                version.startsWith("v1_15") -> v1_15
                version.startsWith("v1_16") -> v1_16
                version.startsWith("v1_17") -> v1_17
                version.startsWith("v1_18") -> v1_18
                version.startsWith("v1_19") -> v1_19
                version.startsWith("v1_20") -> v1_20
                version.startsWith("v1_21") -> v1_21
                else -> UNKNOWN
            }
        }
    }
}