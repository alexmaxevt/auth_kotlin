package ru.evtukhov.android.homework6.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface API {
    // URL запроса (без учета основного адресса)
    @POST("api/v1/authentication")
    suspend fun authenticate(@Body authRequestParams: AuthRequestParams): Response<Token>

    @POST("api/v1/registration")
    suspend fun register(@Body registrationRequestParams: RegistrationRequestParams): Response<Token>
}