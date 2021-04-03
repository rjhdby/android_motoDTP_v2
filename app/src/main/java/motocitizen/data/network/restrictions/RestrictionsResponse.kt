package motocitizen.data.network.restrictions

import com.google.gson.annotations.SerializedName

data class RestrictionsResponse(
    @SerializedName("accidentDescriptions")
    val accidentDescriptions: Boolean = false, // Доступны ли описания аварии
    @SerializedName("accidentWorkers")
    val accidentWorkers: Boolean = false, // Доступны ли участники аварии
    @SerializedName("accidentHistory")
    val accidentHistory: Boolean = false, // Доступна ли история аварии
    @SerializedName("events")
    val events: Boolean = false, // Доступны ли события
    @SerializedName("metrics")
    val metrics: Boolean = false, // Доступен ли просмотр базового списка метрик
    @SerializedName("planWorkers")
    val planWorkers: Boolean = false,  // Доступны ли участники плановой работы
    @SerializedName("push")
    val push: Boolean = false // Доступны ли настройки пушей из приложения
)