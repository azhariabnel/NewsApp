package com.testcase.newsapp.model.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.testcase.newsapp.util.exception.NoConnectionException
import com.testcase.newsapp.view.fragment.NoConnectionDialogFragment
import kotlinx.android.synthetic.main.menu_toolbar.*

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(providesLayout())
        setToolbarComponentClick()
        setSupportBackButton(false)
        setSupportSelectCountry(false)
        onViewCreated()
    }

    protected open fun onClickToolbarComponent(view: View){}

    protected fun setSupportSelectCountry(isSupportSelectCountry: Boolean) {
        selectCountry.visibility = if (isSupportSelectCountry) View.VISIBLE else View.GONE
    }

    private fun setToolbarComponentClick() {
        ivToolbarBackBtn.setOnClickListener {
            onClickToolbarComponent(it)
        }

        selectCountry.setOnClickListener {
            onClickToolbarComponent(it)
        }
    }

    protected fun setSupportBackButton(isEnable: Boolean){
        ivToolbarBackBtn.visibility = if (isEnable) View.VISIBLE else View.GONE
        ivToolbarLogo.visibility = if (isEnable) View.GONE else View.VISIBLE
        ivToolbarLogo.setOnClickListener {
            val isNightTheme = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            when (isNightTheme) {
                Configuration.UI_MODE_NIGHT_YES ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Configuration.UI_MODE_NIGHT_NO ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    @LayoutRes protected abstract fun providesLayout(): Int

    protected abstract fun onViewCreated()

    protected abstract fun onNoInternetRetryCallback(): (() -> Unit)?

    protected fun showError(throwable: Throwable){
        if (throwable is NoConnectionException){
            NoConnectionDialogFragment(onNoInternetRetryCallback()).run {
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
        tvToolbarTitle.text = title
    }
}