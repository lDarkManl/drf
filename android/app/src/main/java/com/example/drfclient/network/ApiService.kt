package com.example.drfclient.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class Capital(
	val capital_city: String,
	val capital_population: Int,
	val author: String,
	val country: String,
)

interface ApiService {
	@GET("api/capitals/")
	suspend fun listCapitals(): List<Capital>

	@POST("api/capitals/")
	suspend fun createCapital(@Body capital: Capital): Capital
}

