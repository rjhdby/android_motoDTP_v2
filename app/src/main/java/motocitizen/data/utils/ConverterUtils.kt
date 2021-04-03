package motocitizen.data.utils

import motocitizen.domain.exceptions.ConvertException

inline fun <reified T> getNotNull(item: T?): T {
    return item ?: throw ConvertException()
}
