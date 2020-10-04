package com.hiroshisasmita.newsapp.model.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.hiroshisasmita.newsapp.util.exception.NoConnectivityException
import com.hiroshisasmita.newsapp.view.fragment.NoConnectivityDialogFragment
import kotlinx.android.synthetic.main.custom_toolbar.*

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(providesLayout())
        setToolbarComponentClick()
        setSupportBackButton(false)
        setSupportSelectCountry(false)
        onViewCreated()
    }

    protected fun setSupportSelectCountry(isSupportSelectCountry: Boolean) {
        selectCountry.visibility = if (isSupportSelectCountry) View.VISIBLE else View.GONE
    }

    private fun setToolbarComponentClick() {
        toolbarBackButton.setOnClickListener {
            onClickToolbarComponent(it)
        }

        selectCountry.setOnClickListener {
            onClickToolbarComponent(it)
        }
    }

    protected open fun onClickToolbarComponent(view: View){}

    protected fun setSupportBackButton(isEnable: Boolean){
        toolbarBackButton.visibility = if (isEnable) View.VISIBLE else View.GONE
        toolbarLogo.visibility = if (isEnable) View.GONE else View.VISIBLE
    }

    @LayoutRes protected abstract fun providesLayout(): Int

    protected abstract fun onViewCreated()

    protected abstract fun onNoInternetRetryCallback(): (() -> Unit)?

    protected fun showError(throwable: Throwable){
        if (throwable is NoConnectivityException){
            NoConnectivityDialogFragment(onNoInternetRetryCallback()).run {
                show(
                    supportFragmentManager.beginTransaction(), ""
                )
            }
        }else{
            Toast.makeText(baseContext, "Error while retrieving data", Toast.LENGTH_LONG)
                .show()
        }
    }

    @Suppress("UNCHECKED_CAST")
    protected open fun <T : ViewModel?> getViewModel(
        viewModelStoreOwner: ViewModelStoreOwner,
        modelClass: Class<out ViewModel?>
    ): T {
        return ViewModelProvider(viewModelStoreOwner).get(modelClass) as T
    }

    protected fun setToolbarTitle(title: String){
        toolbarTitle.text = title
    }
}