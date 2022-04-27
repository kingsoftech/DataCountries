package com.flyconcept.datacountries.model.network

import com.flyconcept.datacountries.model.Country
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface CountriesApi {

    @GET("CatalinStefan/countries/master/countriesV2.json")
    fun getCountries(): Single<List<Country>>
}