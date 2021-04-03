package motocitizen.domain.model.login

data class SignIn(
    val installId: String,
    val token: String,
    val platform: String = "android"
)