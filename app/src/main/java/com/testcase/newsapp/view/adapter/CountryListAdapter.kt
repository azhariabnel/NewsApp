package com.testcase.newsapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.testcase.newsapp.R
import com.testcase.newsapp.model.Country
import kotlinx.android.synthetic.main.item_country.view.*

class CountryListAdapter(private val callback: ((Country)->Unit)?)
    : RecyclerView.Adapter<CountryListAdapter.ViewHolder>() {

    private val countryList = mutableListOf<Country>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countryList[position]
        holder.itemView.ivCountryFlag.setImageResource(country.drawableId)
        holder.itemView.tvCountryName.text = country.name
        holder.itemView.setOnClickListener {
            callback?.invoke(country)
        }
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun updateData(countryList: MutableList<Country>){
        this.countryList.clear()
        this.countryList.addAll(countryList)
        notifyDataSetChanged()
    }
}