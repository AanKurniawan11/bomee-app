package com.example.bomeeapp.data.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class RemoteDataSource @Inject constructor(@ApplicationContext context: Context) {

    companion object {
        const val BASE_URL = "https://b9fc-36-73-144-128.ngrok-free.app/api/"
    }

    val ctx = context
    val isDebug = true

    fun <Api> buildApi(
        api: Class<Api>,
    ): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                getRetrofitClient(ctx)
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }

    fun getRetrofitClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .also { client ->
                if (isDebug) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }
            }
            .addInterceptor(AuthInterceptor(context))
            .addInterceptor (
                ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}