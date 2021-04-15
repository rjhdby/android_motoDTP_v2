package motocitizen.data.network.restrictions

data class Restrictions(
    val accidentDescriptions: Boolean, // Доступны ли описания аварии
    val accidentWorkers: Boolean, // Доступны ли участники аварии
    val accidentHistory: Boolean, // Доступна ли история аварии
    val create: Boolean, // Доступно ли создавать инциденты
    val metrics: Boolean, // Доступен ли просмотр базового списка метрик
    val planWorkers: Boolean,  // Доступны ли участники плановой работы
    val push: Boolean // Доступны ли настройки пушей из приложения
)
