package com.testcase.newsapp.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class PreferenceModule {

    companion object{
        @Provides
        @Singleton
        fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences{
            return context.getSharedPreferences("news_preferences", Context.MODE_PRIVATE)
        }
    }
}