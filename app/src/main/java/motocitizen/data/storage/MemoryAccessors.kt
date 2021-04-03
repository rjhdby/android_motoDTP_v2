package motocitizen.data.storage

import java.util.concurrent.ConcurrentHashMap
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Делегат для получения nullable значений из [HashMapMemory].
 * Если в памяти по ключу не будет значения, вернёт null.
 */
fun <T : Any> HashMapMemory.nullable(): ReadWriteProperty<Any?, T?> {
    return object : ReadWriteProperty<Any?, T?> {
        @Suppress("UNCHECKED_CAST")
        override fun getValue(thisRef: Any?, property: KProperty<*>): T? = get(property.name) as T?

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
            if (value == null) {
                remove(property.name)
            } else {
                put(property.name, value)
            }
        }
    }
}

/**
 * Делегат для получения [HashMap] из [HashMapMemory].
 * Если в памяти по ключу не будет значения, вернёт пустую [HashMap] (и положит его в память).
 */
fun <K, V> HashMapMemory.map(): ReadWriteProperty<Any?, MutableMap<K, V>> {
    return getOrPutProperty { ConcurrentHashMap<K, V>() }
}

inline fun <T : Any> HashMapMemory.getOrPutProperty(crossinline default: () -> T): ReadWriteProperty<Any?, T> {
    return object : ReadWriteProperty<Any?, T> {
        @Suppress("UNCHECKED_CAST")
        override fun getValue(thisRef: Any?, property: KProperty<*>): T =
            getOrPut(property.name, default) as T

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            put(property.name, value)
        }
    }
}