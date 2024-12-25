package com.robotutor.seedData.utils

import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

fun <T : Any> mapToDataClass(map: Map<String, String>, clazz: KClass<T>): T {
    val constructor = clazz.primaryConstructor
        ?: throw IllegalArgumentException("Class ${clazz.simpleName} must have a primary constructor")

    val args = constructor.parameters.associateWith { param ->
        val value = map[param.name]
        when {
            value == null -> null
            param.type.classifier == Int::class -> value.toIntOrNull()
            param.type.classifier == Double::class -> value.toDoubleOrNull()
            param.type.classifier == Boolean::class -> value.toBooleanStrictOrNull()
            param.type.classifier == List::class -> value.split("|").map { it.trim() }
            else -> value
        }
    }

    return constructor.callBy(args)
}

