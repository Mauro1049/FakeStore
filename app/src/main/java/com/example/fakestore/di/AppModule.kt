package com.example.fakestore.di

import com.example.fakestore.data.APIFakeStore
import com.example.fakestore.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /*
    * Esta funcion crea una instancia de Retrofit, y la utiliza para crear una instancia de APIFakeStore
    * */
    @Singleton
    @Provides
    fun providesRetroFit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /*
    * Esta funcion crea una instancia de APIFakeStore, y la utiliza para crear una instancia de Retrofit
    * */
    @Singleton
    @Provides
    fun providesAPiFakeStore(retrofit: Retrofit): APIFakeStore {
        return retrofit.create(APIFakeStore::class.java)
    }
}