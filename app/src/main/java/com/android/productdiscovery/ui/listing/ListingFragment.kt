package com.android.productdiscovery.ui.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.productdiscovery.R
import com.android.productdiscovery.ui.base.BaseFragment
import com.android.productdiscovery.ui.detail.DetailActivity
import com.android.productdiscovery.utils.extension.adjustTopMargin
import com.android.productdiscovery.utils.extension.hideKeyboard
import com.android.productdiscovery.utils.extension.obtainViewModel
import com.android.productdiscovery.utils.extension.showToast
import kotlinx.android.synthetic.main.fragment_listing.*
import kotlinx.android.synthetic.main.including_error_layout.*

/**
 * @author ThanhDT
 * @since 2019-07-28
 */
class ListingFragment : BaseFragment() {

    private lateinit var viewModel: ListingViewModel

    private var listingAdapter: ListingAdapter? = null

    private var isLoadMore = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_listing, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initUI()

        viewModel = obtainViewModel(ListingViewModel::class.java).apply {
            errorMsg.observe(this@ListingFragment, Observer {
                hideProgress()

                if (isLoadMore) {
                    listingAdapter?.removeLoadingView()
                    isLoadMore = false
                }

                if (listingAdapter?.itemCount == 0) {
                    groupError.visibility = View.VISIBLE
                    tvError.text = it
                } else {
                    showToast(it)
                }
            })

            firstPageData.observe(this@ListingFragment, Observer {
                listingAdapter?.setData(it)
            })

            pageData.observe(this@ListingFragment, Observer {
                if (isLoadMore) {
                    listingAdapter?.removeLoadingView()
                    isLoadMore = false
                }

                listingAdapter?.addData(it)
            })

            loadingStatus.observe(this@ListingFragment, Observer {
                if (it) showProgress() else hideProgress()
            })
        }
    }

    private fun initUI() {
        adjustTopMargin(btnBack)
        initProductList()
        initSearchListener()

        btnTryAgain.setOnClickListener { performSearch() }
        btnBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun initSearchListener() {
        edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()

                return@setOnEditorActionListener true
            }

            false
        }
    }

    private fun performSearch() {
        if (edtSearch.text.isNotBlank()) {
            hideKeyboard()
            viewModel.searchProduct(edtSearch.text.toString(), 1)
            groupError.visibility = View.GONE
            edtSearch.clearFocus()
        } else {
            showToast(R.string.input_name_product_code_to_search)
        }
    }

    private fun initProductList() {
        listingAdapter = ListingAdapter(selectAction = { product ->
            DetailActivity.startActivity(requireContext(), product.sku)
        })

        rvListing.apply {
            adapter = listingAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        recyclerView.adapter?.let {
                            val totalItemCount = it.itemCount
                            val lastItemIndex =
                                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                            if (lastItemIndex > totalItemCount - 8 && !isLoadMore) {
                                isLoadMore = true
                                listingAdapter?.addLoadingView()
                                viewModel.searchProduct()
                            }
                        }
                    }
                }
            })
        }
    }
}