package com.flyconcept.datacountries.di

import com.flyconcept.datacountries.model.network.CountriesService
import com.flyconcept.datacountries.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service:CountriesService)
    fun inject(viewModel: ListViewModel)
}