package com.flyconcept.datacountries.model.network

import com.flyconcept.datacountries.model.Country
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountriesService {

    private val api: CountriesApi
    init{
        api = Retrofit.
        Builder().
        baseUrl(Companion.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory( RxJava3CallAdapterFactory.create())
            .build().create(CountriesApi::class.java)
    }

    fun getCountries():Single<List<Country>>{
        return api.getCountries()
    }

    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com/"
    }
}