package it.futurecraft.quark.utils

object Environment {
    val environment: Type by lazy {
        Type.from(System.getProperty("environment"))
    }

    val development: Boolean
        get() = environment == Type.DEVELOPMENT

    val production: Boolean
        get() = environment == Type.PRODUCTION

    enum class Type {
        DEVELOPMENT,
        PRODUCTION,
        UNKNOWN;

        companion object {
            fun from(value: String): Type = when(value.lowercase()) {
                "development" -> DEVELOPMENT
                "production" -> PRODUCTION
                else -> UNKNOWN
            }
        }
    }
}