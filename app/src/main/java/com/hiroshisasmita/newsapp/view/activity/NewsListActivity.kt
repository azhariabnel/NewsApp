package com.hiroshisasmita.newsapp.view.activity

import android.view.View
import android.widget.Toast
import com.hiroshisasmita.newsapp.R
import com.hiroshisasmita.newsapp.model.Article
import com.hiroshisasmita.newsapp.model.base.BaseActivity
import com.hiroshisasmita.newsapp.model.base.BaseRecyclerViewPagingAdapter.PagingAdapterListener
import com.hiroshisasmita.newsapp.model.base.BaseRepository
import com.hiroshisasmita.newsapp.model.repository.NewsRepository
import com.hiroshisasmita.newsapp.view.adapter.NewsListAdapter
import com.hiroshisasmita.newsapp.viewmodel.NewsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_news_list.*

@AndroidEntryPoint
class NewsListActivity : BaseActivity() {
    private lateinit var viewModel: NewsListViewModel
    private var currentPage = 1
    private lateinit var adapter: NewsListAdapter

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
        initRv()
        observeData()
        loadFirstPage()
        setToolbarTitle("TechNews")
        swiperRefresh.setOnRefreshListener {
            setLoadingVisibility(true)
            loadFirstPage()
        }
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

    private fun initRv() {
        adapter = NewsListAdapter().apply {
            setRecyclerView(recyclerView)
            listener = object: PagingAdapterListener<Article> {
                override fun loadMore(page: Int) {
                    currentPage = page
                    viewModel.retrieveNews(currentPage)
                }

                override fun onItemClick(item: Article) {
                    Toast.makeText(baseContext, item.title, Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onStateLoadingChange(isLoading: Boolean) {
                    setLoadingVisibility(isLoading)
                }
            }
        }
    }

    private fun setLoadingVisibility(isLoading: Boolean) {
        loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}