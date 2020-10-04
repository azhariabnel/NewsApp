package com.hiroshisasmita.newsapp.view.fragment

import android.content.res.TypedArray
import android.os.Bundle
import android.os.ProxyFileDescriptorCallback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.hiroshisasmita.newsapp.R
import com.hiroshisasmita.newsapp.model.Country
import com.hiroshisasmita.newsapp.view.adapter.CountryListAdapter
import kotlinx.android.synthetic.main.fragment_dialog_choose_country.*
import java.util.*

class ChooseCountryDialogFragment(
    private val countryList: MutableList<Country>,
    private val callback: ((Country)->Unit)?
): DialogFragment() {
    private lateinit var adapter: CountryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_dialog_choose_country,container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        adapter.updateData(countryList)
    }

    private fun initView() {
        adapter = CountryListAdapter(callback).also {
            recyclerView.adapter = it
        }

        closeDialog.setOnClickListener {
            dismiss()
        }
    }
}