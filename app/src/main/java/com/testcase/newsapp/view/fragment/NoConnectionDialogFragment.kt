package com.testcase.newsapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.testcase.newsapp.R
import kotlinx.android.synthetic.main.fragment_fullscreen_dialog_no_connection.*

class NoConnectionDialogFragment(private val onRetryCallback: (() -> Unit)?): DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AppTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fullscreen_dialog_no_connection,container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnRetryConnection.setOnClickListener {
            onRetryCallback?.invoke()
            dismiss()
        }
    }
}