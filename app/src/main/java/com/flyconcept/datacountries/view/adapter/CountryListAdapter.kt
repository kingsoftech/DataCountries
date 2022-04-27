package com.flyconcept.datacountries.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flyconcept.datacountries.databinding.ItemCountryBinding
import com.flyconcept.datacountries.model.Country
import com.flyconcept.datacountries.util.getProgressDrawable
import com.flyconcept.datacountries.util.loadImage
import java.util.concurrent.CountDownLatch

class CountryListAdapter(private var activity: Activity,private var countryList: ArrayList<Country>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemCountryBinding  = ItemCountryBinding.inflate(LayoutInflater.from(activity), parent, false)
        return MyViewHolder(itemCountryBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = countryList[position]
        if(holder is MyViewHolder){
            holder.tvName.text = model.countryName
            holder.tvCapital.text = model.capital
            holder.ivCountryFlag.loadImage(model.flag, holder.progressDrawable)
        }
    }

    override fun getItemCount(): Int {
       return countryList.size
    }

    fun updateCountries(newCountry: List<Country>){
        countryList.clear()
        countryList.addAll(newCountry)
        notifyDataSetChanged()

    }
    inner class MyViewHolder(binding:ItemCountryBinding):RecyclerView.ViewHolder(binding.root){
        val tvName = binding.tvName
        val tvCapital = binding.tvCapital
        val ivCountryFlag = binding.ivCountryFlag
        val progressDrawable = getProgressDrawable(activity)

    }
}