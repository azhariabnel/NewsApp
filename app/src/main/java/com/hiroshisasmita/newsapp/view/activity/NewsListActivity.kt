package com.hiroshisasmita.newsapp.view.activity

import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import com.hiroshisasmita.newsapp.R
import com.hiroshisasmita.newsapp.model.Article
import com.hiroshisasmita.newsapp.model.Country
import com.hiroshisasmita.newsapp.model.base.BaseActivity
import com.hiroshisasmita.newsapp.model.base.BaseRecyclerViewPagingAdapter.PagingAdapterListener
import com.hiroshisasmita.newsapp.model.repository.NewsRepository
import com.hiroshisasmita.newsapp.util.Const
import com.hiroshisasmita.newsapp.view.adapter.NewsListAdapter
import com.hiroshisasmita.newsapp.view.fragment.ChooseCountryDialogFragment
import com.hiroshisasmita.newsapp.viewmodel.NewsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_news_list.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import javax.inject.Inject

@AndroidEntryPoint
class NewsListActivity : BaseActivity() {
    @Inject lateinit var preferences: SharedPreferences
    private lateinit var viewModel: NewsListViewModel
    private var currentPage = 1
    private lateinit var adapter: NewsListAdapter
    private lateinit var chooseCountryDialogFragment: ChooseCountryDialogFragment
    private var countryList = mutableListOf<Country>()

    override fun onNoInternetRetryCallback(): () -> Unit {
        return object : () -> Unit {
            override fun invoke() {
                loadFirstPage()
            }
        }
    }

    override fun providesLayout(): Int {
        return R.layout.activity_news_list
    }

    override fun onViewCreated() {
        viewModel = getViewModel(this, NewsListViewModel::class.java)
        initCountryData()
        initView()
        observeData()
        loadFirstPage()
        initCountryFlagView()
        setSupportSelectCountry(true)
        setToolbarTitle("TechNews")
        swiperRefresh.setOnRefreshListener {
            setLoadingVisibility(true)
            loadFirstPage()
        }
    }

    private fun initCountryFlagView() {
        val country = countryList.find {
            it.code == preferences.getString(Const.COUNTRY_PREFERENCE,"id")
        }
        if (country != null) toolbarCountryFlag.setImageResource(country.drawableId)
    }

    private fun initCountryData() {
        val countryCodeList = mutableListOf<String>().also {
            it.addAll(resources.getStringArray(R.array.countryCode))
        }
        val countryNameList = mutableListOf<String>().also {
            it.addAll(resources.getStringArray(R.array.countryName))
        }

        val typedArray = resources.obtainTypedArray(R.array.countryFlag)

        countryList = mutableListOf()

        for (idx in 0 until countryCodeList.size){
            countryList.add(
                Country(
                    countryCodeList[idx],
                    countryNameList[idx],
                    typedArray.getResourceId(idx,0)
                )
            )
        }

        typedArray.recycle()
    }

    private fun loadFirstPage() {
        currentPage = 1
        viewModel.retrieveNews(currentPage)
    }

    private fun observeData() {
        viewModel.getObservable().observe(this, {
            swiperRefresh.isRefreshing = false
            if (it.data != null && it.data?.articles != null) {
                if (currentPage == 1) {
                    adapter.updateData(it.data!!.articles, it.data!!.totalPage!!)
                } else {
                    adapter.addData(it.data!!.articles)
                    scrollAfterLoadMore()
                }
            } else {
                showError(it.error!!)
            }
        })
    }

    private fun scrollAfterLoadMore() {
        recyclerView.post {
            recyclerView.smoothScrollToPosition(adapter.itemCount - (NewsRepository.pageSize - 1))
        }
    }

    private fun initView() {
        adapter = NewsListAdapter().apply {
            setRecyclerView(recyclerView)
            listener = object: PagingAdapterListener<Article> {
                override fun loadMore(page: Int) {
                    currentPage = page
                    viewModel.retrieveNews(currentPage)
                }

                override fun onItemClick(item: Article) {
                    showDetail(item)
                }

                override fun onStateLoadingChange(isLoading: Boolean) {
                    setLoadingVisibility(isLoading)
                }
            }
        }

        chooseCountryDialogFragment = ChooseCountryDialogFragment(countryList){
            chooseCountryDialogFragment.dismiss()
            setCountryChange(it)
        }
    }

    private fun setCountryChange(country: Country) {
        preferences.edit().putString(Const.COUNTRY_PREFERENCE, country.code).apply()
        toolbarCountryFlag.setImageResource(country.drawableId)
        loadFirstPage()
    }

    private fun showDetail(item: Article) {
        Intent(this, NewsDetailActivity::class.java).apply {
            putExtra(Const.ARTICLE_EXTRA, item)
            startActivity(this)
        }
    }

    private fun setLoadingVisibility(isLoading: Boolean) {
        loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onClickToolbarComponent(view: View) {
        when (view.id){
            R.id.selectCountry -> {
                chooseCountryDialogFragment.show(supportFragmentManager.beginTransaction(),"")
            }
        }
    }
}