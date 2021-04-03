package motocitizen.data.storage

import com.google.gson.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.ISODateTimeFormat
import java.lang.reflect.Type

class DateTimeSerializer : JsonDeserializer<DateTime?>, JsonSerializer<DateTime?> {

    companion object {
        private val DATE_FORMAT: DateTimeFormatter = ISODateTimeFormat.date()
    }

    @Throws(JsonParseException::class)
    override fun deserialize(
        jjsonElement: JsonElement,
        type: Type?,
        context: JsonDeserializationContext?,
    ): DateTime? {
        val dateAsString = jjsonElement.asString
        return if (dateAsString.isEmpty()) {
            null
        } else {
            DATE_FORMAT.parseDateTime(dateAsString)
        }
    }

    override fun serialize(
        src: DateTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?,
    ): JsonElement {
        val json = if (src == null) {
            ""
        } else {
            DATE_FORMAT.print(src)
        }
        return JsonPrimitive(json)
    }
}