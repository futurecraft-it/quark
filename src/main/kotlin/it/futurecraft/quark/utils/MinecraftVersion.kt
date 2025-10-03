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