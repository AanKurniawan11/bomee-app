package com.example.bomeeapp.di

import com.example.bomeeapp.data.local.UserPreferences
import com.example.bomeeapp.data.network.RemoteDataSource
import com.example.bomeeapp.data.network.auth.AuthApi
import com.example.bomeeapp.data.network.auth.AuthRepository
import com.example.bomeeapp.data.network.booking.BookingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAuthApi(
        remoteDataSource: RemoteDataSource
    ): AuthApi {
        return remoteDataSource.buildApi(AuthApi::class.java)
    }


    @Singleton
    @Provides
    fun provideBookingApi(
        remoteDataSource: RemoteDataSource
    ): BookingApi {
        return remoteDataSource.buildApi(BookingApi::class.java)
    }



}