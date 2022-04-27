package com.flyconcept.datacountries.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.flyconcept.datacountries.R
import com.flyconcept.datacountries.databinding.ActivityMainBinding
import com.flyconcept.datacountries.view.adapter.CountryListAdapter
import com.flyconcept.datacountries.viewmodel.ListViewModel

class MainActivity : AppCompatActivity() {
    var activityMainBinding: ActivityMainBinding? = null
    lateinit var viewModel: ListViewModel
    private val countriesAdapter = CountryListAdapter(this, arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding!!.root)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()
        activityMainBinding!!.countryList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = countriesAdapter
        }
        observeViewModel()

    }
    fun observeViewModel()
    {
        viewModel.countries.observe(this, Observer {
                countries ->
            countries?.let {
                activityMainBinding!!.countryList.visibility = View.VISIBLE
                countriesAdapter.updateCountries(it)
            }
        })

        viewModel.countryLoadError.observe(this) { isError ->
            isError?.let {
                activityMainBinding!!.listError.visibility = if (it) View.VISIBLE else View.GONE
            }
        }

        viewModel.loading.observe(this){
                isLoading->
            isLoading?.let{
                activityMainBinding!!.loadingView.visibility = if(it)  View.VISIBLE else View.GONE

                if(it){
                    activityMainBinding!!.listError.visibility = View.GONE
                    activityMainBinding!!.countryList.visibility = View.GONE
                }
            }
        }
    }



}