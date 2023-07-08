package com.moveis.new_fabw.helper

import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

class ConverterHelper {
    fun <T : Any> MapearObjeto(obj: T): Map<String, Any?> {
        return (obj::class as KClass<T>).memberProperties.associate { prop ->
            prop.name to prop.get(obj)?.let { value ->
                if (value::class.isData) {
                    MapearObjeto(value)
                } else {
                    value
                }
            }
        }
    }
}