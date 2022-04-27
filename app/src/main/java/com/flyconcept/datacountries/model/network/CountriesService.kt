package com.flyconcept.datacountries.model.network

import com.flyconcept.datacountries.di.DaggerApiComponent
import com.flyconcept.datacountries.model.Country
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class CountriesService {
    @Inject
    lateinit var api: CountriesApi
    init{
        DaggerApiComponent.create().inject(this)
    }

    fun getCountries():Single<List<Country>>{
        return api.getCountries()
    }


}