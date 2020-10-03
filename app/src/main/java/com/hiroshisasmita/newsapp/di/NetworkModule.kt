package com.hiroshisasmita.newsapp.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hiroshisasmita.newsapp.BuildConfig
import com.hiroshisasmita.newsapp.model.retrofit.NewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {
    companion object{
        @Provides
        @Singleton
        fun providesOkHttpClient(): OkHttpClient{
            return OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", "827c1dc391fa406b9211e5933338506e")
                    .build()
                chain.proceed(request)
            }
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addNetworkInterceptor(StethoInterceptor())
                .build()
        }

        @Provides
        @Singleton
        fun providesGson(): Gson{
            return GsonBuilder()
                .create()
        }

        @Provides
        @Singleton
        fun providesService(client: OkHttpClient, gson: Gson): NewsService {
            return Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(NewsService::class.java)
        }
    }
}