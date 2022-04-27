package com.flyconcept.datacountries.di


import com.flyconcept.datacountries.model.network.CountriesApi
import com.flyconcept.datacountries.model.network.CountriesService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
@Module
class ApiModule {

        private val BASE_URL = "https://raw.githubusercontent.com/"
    @Provides
    fun provideCountriesApi(): CountriesApi{
        return Retrofit.
        Builder().
        baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory( RxJava3CallAdapterFactory.create())
            .build().create(CountriesApi::class.java)
    }

    @Provides
    fun provideCountriesServices(): CountriesService{
        return CountriesService()
    }


}