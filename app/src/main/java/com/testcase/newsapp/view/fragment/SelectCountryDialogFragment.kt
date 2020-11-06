package com.testcase.newsapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.testcase.newsapp.R
import com.testcase.newsapp.model.Country
import com.testcase.newsapp.view.adapter.CountryListAdapter
import kotlinx.android.synthetic.main.fragment_fullscreen_dialog_select_country.*

class SelectCountryDialogFragment(
    private val countryList: MutableList<Country>,
    private val callback: ((Country)->Unit)?
): DialogFragment() {
    private lateinit var adapter: CountryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_fullscreen_dialog_select_country,container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDialog()
        adapter.updateData(countryList)
    }

    private fun initDialog() {
        adapter = CountryListAdapter(callback).also {
            rvCountry.adapter = it
        }
        ibCloseDialog.setOnClickListener {
            dismiss()
        }
    }
}