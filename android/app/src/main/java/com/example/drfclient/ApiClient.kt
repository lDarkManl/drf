package com.example.drfclient

import android.util.Log
import com.example.drfclient.network.ApiService
import com.example.drfclient.network.Capital
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

	private const val BASE_URL = "http://10.0.2.2:8000/"

	private val loggingInterceptor = HttpLoggingInterceptor { message ->
		Log.d("HTTP", message)
	}.apply {
		level = HttpLoggingInterceptor.Level.BODY
	}

	private val httpClient: OkHttpClient by lazy {
		OkHttpClient.Builder()
			.addInterceptor(loggingInterceptor)
			.build()
	}

	private val retrofit: Retrofit by lazy {
		Retrofit.Builder()
			.baseUrl(BASE_URL)
			.client(httpClient)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}

    val service: ApiService by lazy {
		retrofit.create(ApiService::class.java)
	}

	suspend fun fetchCapitals(): List<Capital> = service.listCapitals()

	suspend fun addCapital(capital: Capital): Capital = service.createCapital(capital)
}

