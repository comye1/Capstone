package com.comye1.capstone.di

import android.content.Context
import com.comye1.capstone.network.CapstoneApi
import com.comye1.capstone.repository.CapstoneRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCapstoneRepository(
        api: CapstoneApi,
        @ApplicationContext context: Context
    ) = CapstoneRepository(api, context)

    @Singleton
    @Provides
    fun provideCapstoneApi(): CapstoneApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.naver.com")
            .build()
            .create(CapstoneApi::class.java)
    }
}