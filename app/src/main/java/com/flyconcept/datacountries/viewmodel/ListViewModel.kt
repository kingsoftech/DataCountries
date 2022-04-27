package com.flyconcept.datacountries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flyconcept.datacountries.model.Country
import com.flyconcept.datacountries.model.network.CountriesService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class ListViewModel:ViewModel() {
    private val countriesService = CountriesService()
    private val disposable = CompositeDisposable()

    val countries = MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<Boolean>()
     val loading = MutableLiveData<Boolean>()

    fun refresh(){
        fetchCountries()
    }
    private fun  fetchCountries(){
       loading.value = true
        disposable.add(
            countriesService.getCountries()
                    //doing the work on background thread
                .subscribeOn(Schedulers.newThread())
                    //but getting the result on a main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(value: List<Country>) {
                        countries.value = value
                        countryLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        countryLoadError.value = true
                        loading.value = false
                    }

                })
        )


    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}