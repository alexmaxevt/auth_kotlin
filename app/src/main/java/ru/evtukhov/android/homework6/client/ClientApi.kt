package ru.evtukhov.android.homework6.client

import io.ktor.client.HttpClient
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.http.ContentType
import io.ktor.util.KtorExperimentalAPI

object Api {
    const val urlJson: String = "https://raw.githubusercontent.com/alexmaxevt/Gson/master/postlist.json"

    @KtorExperimentalAPI
    val client = HttpClient{
        install(JsonFeature) {
            acceptContentTypes = listOf(
                ContentType.Text.Plain,
                ContentType.Application.Json
            )
            serializer = GsonSerializer()
        }
    }
}