package com.testcase.newsapp.module

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.testcase.newsapp.BuildConfig
import com.testcase.newsapp.model.retrofit.NewsService
import com.testcase.newsapp.util.interceptor.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
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
        fun providesOkHttpClient(
            networkConnectionInterceptor: NetworkConnectionInterceptor,
            headerInterceptor: Interceptor
        ): OkHttpClient{
            return OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(networkConnectionInterceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addNetworkInterceptor(StethoInterceptor())
                .build()
        }

        @Provides
        @Singleton
        fun providesHeaderInterceptor(): Interceptor{
            return Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", "f760903f523d4418974505ec72db7ab9")
                    .build()
                chain.proceed(request)
            }
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

        @Provides
        @Singleton
        fun providesNetworkConnectionInterceptor(
            @ApplicationContext context: Context
        ):NetworkConnectionInterceptor{
            return NetworkConnectionInterceptor(context)
        }

        @Provides
        @Singleton
        fun providesGson(): Gson{
            return GsonBuilder()
                .create()
        }
    }
}