package motocitizen.data.storage

import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

/**
 * Память на основе [ConcurrentHashMap]. Используется в Storage'ах.
 *
 * Для работы со значениями из памяти удобно использовать делегаты:
 * ```
 *  interface FooStorage {
 *      operator fun get(id: String): Foo?
 *      operator fun set(id: String, foo: Foo)
 *  }
 *
 *  class FooInMemoryStorage(memory: SessionMemory) : FooStorage {
 *      // Делегируем чтение и запись поля memory, чтобы при очистке памяти кэш очистился
 *      private val foos: MutableMap<String, Foo> by memory.map()
 *
 *      operator fun get(id: String): Foo? = foos[id]
 *
 *      operator fun set(id: String, foo: Foo) {
 *          foos[id] = foo
 *      }
 *  }
 * @see SessionMemory
 */
abstract class HashMapMemory : MutableMap<String, Any?> by ConcurrentHashMap()

/**
 * Память, доступная в течение сессии.
 * Очищается после деавторизации пользователя или при закрытии приложения.
 */
class SessionMemory @Inject constructor() : HashMapMemory()