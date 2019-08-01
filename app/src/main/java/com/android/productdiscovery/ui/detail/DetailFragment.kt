package com.android.productdiscovery.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.android.productdiscovery.R
import com.android.productdiscovery.domain.remote.pojo.response.Product
import com.android.productdiscovery.ui.base.BaseFragment
import com.android.productdiscovery.ui.detail.DetailActivity.Companion.SKU_EXTRA
import com.android.productdiscovery.utils.extension.adjustTopMargin
import com.android.productdiscovery.utils.extension.obtainViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * @author ThanhDT
 * @since 2019-08-01
 */
class DetailFragment : BaseFragment(), View.OnClickListener {

    private lateinit var viewModel: DetailViewModel
    private var sku = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adjustTopMargin(btnBack)

        arguments?.getString(SKU_EXTRA, "")?.let { sku = it }

        initUI()

        viewModel = obtainViewModel(DetailViewModel::class.java).apply {
            productData.observe(this@DetailFragment, Observer {
                updateProductData(it)
            })

            getProductDetail(sku)
        }
    }

    private fun initUI() {
        btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            btnBack.id -> requireActivity().onBackPressed()
        }
    }

    private fun updateProductData(product: Product) {

    }
}