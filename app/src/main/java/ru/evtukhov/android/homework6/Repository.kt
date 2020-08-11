package ru.evtukhov.android.homework6

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.evtukhov.android.homework6.api.API
import ru.evtukhov.android.homework6.api.AuthRequestParams
import ru.evtukhov.android.homework6.api.RegistrationRequestParams

object Repository {

    // Ленивое создание Retrofit экземпляра
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://ktor-auth.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    // Ленивое создание API
    private val API: API by lazy {
        retrofit.create(ru.evtukhov.android.homework6.api.API::class.java)
    }

    suspend fun authenticate(login: String, password: String) = API.authenticate(
        AuthRequestParams(login, password)
    )

    suspend fun register(login: String, password: String) =
        API.register(
            RegistrationRequestParams(
                login,
                password
            )
        )
}