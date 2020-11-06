package com.testcase.newsapp.view.activity

import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import com.testcase.newsapp.R
import com.testcase.newsapp.model.Article
import com.testcase.newsapp.model.Country
import com.testcase.newsapp.model.base.BaseActivity
import com.testcase.newsapp.model.base.InfiniteRecycleViewPagingAdapter.PagingAdapterListener
import com.testcase.newsapp.model.repository.NewsRepository
import com.testcase.newsapp.util.Const
import com.testcase.newsapp.view.adapter.NewsListAdapter
import com.testcase.newsapp.view.fragment.SelectCountryDialogFragment
import com.testcase.newsapp.viewmodel.NewsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_news_list.*
import kotlinx.android.synthetic.main.menu_toolbar.*
import javax.inject.Inject

@AndroidEntryPoint
class NewsListActivity : BaseActivity() {
    @Inject lateinit var preferences: SharedPreferences
    private lateinit var viewModel: NewsListViewModel
    private var currentPage = 1
    private lateinit var adapter: NewsListAdapter
    private lateinit var selectCountryDialogFragment: SelectCountryDialogFragment
    private var countryList = mutableListOf<Country>()

    override fun providesLayout(): Int {
        return R.layout.activity_news_list
    }

    override fun onViewCreated() {
        viewModel = getViewModel(this, NewsListViewModel::class.java)
        initCountryData()
        initView()
        observeData()
        initPage()
        initCountryFlagView()
        setSupportSelectCountry(true)
        setToolbarTitle("Today Tech News")
        swipeRefresh.setOnRefreshListener {
            setLoadingVisibility(true)
            initPage()
        }
    }

    private fun initPage() {
        currentPage = 1
        viewModel.getNews(currentPage)
    }

    private fun observeData() {
        viewModel.getObservable().observe(this, {
            swipeRefresh.isRefreshing = false
            if (it.data != null && it.data?.articles != null) {
                if (currentPage == 1) {
                    adapter.updateData(it.data!!.articles, it.data!!.totalPage!!)
                } else {
                    adapter.addData(it.data!!.articles)
                    loadMore()
                }
            } else {
                showError(it.error!!)
            }
        })
    }

    private fun loadMore() {
        rvNewsList.post {
            rvNewsList.smoothScrollToPosition(adapter.itemCount - (NewsRepository.pageSize - 1))
        }
    }

    private fun initView() {
        adapter = NewsListAdapter().apply {
            setRecyclerView(rvNewsList)
            listener = object: PagingAdapterListener<Article> {
                override fun loadMore(page: Int) {
                    currentPage = page
                    viewModel.getNews(currentPage)
                }

                override fun onItemClick(item: Article) {
                    gotoDetail(item)
                }

                override fun onStateLoadingChange(isLoading: Boolean) {
                    setLoadingVisibility(isLoading)
                }
            }
        }

        selectCountryDialogFragment = SelectCountryDialogFragment(countryList){
            selectCountryDialogFragment.dismiss()
            setCountryChange(it)
        }
    }

    private fun initCountryFlagView() {
        val country = countryList.find {
            it.code == preferences.getString(Const.COUNTRY_PREFERENCE,"id")
        }
        if (country != null) ivToolbarCountryFlag.setImageResource(country.drawableId)
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

    private fun setCountryChange(country: Country) {
        preferences.edit().putString(Const.COUNTRY_PREFERENCE, country.code).apply()
        ivToolbarCountryFlag.setImageResource(country.drawableId)
        rvNewsList.smoothScrollToPosition(0)
        initPage()
    }

    private fun gotoDetail(item: Article) {
        Intent(this, NewsDetailActivity::class.java).apply {
            putExtra(Const.ARTICLE_EXTRA, item)
            startActivity(this)
        }
    }

    private fun setLoadingVisibility(isLoading: Boolean) {
        loadingBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onClickToolbarComponent(view: View) {
        when (view.id){
            R.id.selectCountry -> {
                selectCountryDialogFragment.show(supportFragmentManager.beginTransaction(),"")
            }
        }
    }

    override fun onNoInternetRetryCallback(): () -> Unit {
        return object : () -> Unit {
            override fun invoke() {
                initPage()
            }
        }
    }
}