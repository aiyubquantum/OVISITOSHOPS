package com.example.data.network

import com.example.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    @Volatile
    var authToken: String? = null

    @Volatile
    var customClientId: String = if (BuildConfig.OVISITO_CLIENT_ID.isNotBlank()) BuildConfig.OVISITO_CLIENT_ID else BuildConfig.CLIENT_ID

    @Volatile
    var customClientSecret: String = if (BuildConfig.OVISITO_CLIENT_SEC.isNotBlank()) BuildConfig.OVISITO_CLIENT_SEC else BuildConfig.CLIENT_SECRET

    private val authInterceptor = Interceptor { chain ->
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("X-Client-ID", customClientId)
            .header("X-Client-Secret", customClientSecret)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")

        authToken?.let { token ->
            requestBuilder.header("Authorization", "Bearer $token")
        }

        chain.proceed(requestBuilder.build())
    }

    private val okHttpClient: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logging)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    private val moshi: Moshi by lazy {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    val apiService: OvisitoApiService by lazy {
        var rawBase = if (BuildConfig.OVISITO_API_BASE_U.isNotBlank()) {
            BuildConfig.OVISITO_API_BASE_U
        } else {
            BuildConfig.API_BASE_URL
        }
        val baseUrl = when {
            rawBase.contains("/index.php/api/v2") -> if (rawBase.endsWith("/")) rawBase else "$rawBase/"
            rawBase.contains("/api/v2") -> {
                val replaced = rawBase.replace("/api/v2", "/index.php/api/v2")
                if (replaced.endsWith("/")) replaced else "$replaced/"
            }
            else -> {
                val trimmed = rawBase.trimEnd('/')
                "$trimmed/index.php/api/v2/"
            }
        }

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(OvisitoApiService::class.java)
    }
}
