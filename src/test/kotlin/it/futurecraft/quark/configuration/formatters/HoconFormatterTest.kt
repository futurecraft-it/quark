/*
 *     quark
 *     Copyright (C) 2026  FUTURECRAFT
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

import kotlinx.serialization.Serializable
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@Serializable
data class Test1(val a: String, val b: Test2) {

    @Serializable
    data class Test2(val a: String, val b: Int)
}

internal class HoconFormatterTest {
    val data = Test1("ciao :=)", Test1.Test2("Ciao Due!", 120))

    @Test
    fun serialize() {
        val str = HoconFormatter.serialize(data, Test1.serializer())
        println(str)
    }
}