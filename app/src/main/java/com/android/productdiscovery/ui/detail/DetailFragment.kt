package com.android.productdiscovery.ui.detail

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.android.productdiscovery.R
import com.android.productdiscovery.domain.remote.pojo.response.Product
import com.android.productdiscovery.domain.remote.pojo.response.SellingStatus
import com.android.productdiscovery.ui.base.BaseFragment
import com.android.productdiscovery.ui.detail.DetailActivity.Companion.SKU_EXTRA
import com.android.productdiscovery.utils.CurrencyUtils
import com.android.productdiscovery.utils.extension.obtainViewModel
import com.android.productdiscovery.utils.extension.replaceFragment
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.including_error_layout.*
import timber.log.Timber

/**
 * @author ThanhDT
 * @since 2019-08-01
 */
class DetailFragment : BaseFragment(), View.OnClickListener {

    private lateinit var viewModel: DetailViewModel
    private var sku = ""

    private var imageAdapter: ImageAdapter? = null
    private var product = Product()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.getString(SKU_EXTRA, "")?.let { sku = it }

        initUI()

        viewModel = obtainViewModel(DetailViewModel::class.java).apply {

            cartItemCount.observe(this@DetailFragment, Observer {
                if (it > 0) {
                    tvCartCount.visibility = View.VISIBLE
                    tvCartCount.text = it.toString()
                } else {
                    tvCartCount.visibility = View.GONE
                }
            })

            productData.observe(this@DetailFragment, Observer {
                svContent.visibility = View.VISIBLE
                updateProductData(it)
            })

            loadingStatus.observe(this@DetailFragment, Observer {
                if (it) showProgress() else hideProgress()
            })

            changeSlide.observe(this@DetailFragment, Observer {
                if (vpProductImage.currentItem == (imageAdapter?.itemCount ?: 0) - 1) {
                    vpProductImage.currentItem = 0
                } else {
                    vpProductImage.currentItem = vpProductImage.currentItem + 1
                }
            })

            productTotalPrice.observe(this@DetailFragment, Observer {
                tvTotalPrice.text = CurrencyUtils.formatCurrency(it)
            })

            productCount.observe(this@DetailFragment, Observer {
                tvBuyCount.text = it.toString()
            })

            errorMsg.observe(this@DetailFragment, Observer {
                groupCart.visibility = View.GONE
                svContent.visibility = View.GONE
                groupError.visibility = View.VISIBLE
                tvError.text = it
            })

            getProductDetail(this@DetailFragment.sku)
        }
    }

    private fun initUI() {
        btnBack.setOnClickListener(this)
        ivPlus.setOnClickListener(this)
        ivMinus.setOnClickListener(this)
        btnTryAgain.setOnClickListener(this)

        imageAdapter = ImageAdapter()
        vpProductImage.apply {
            adapter = imageAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    viewModel.startChangeSlideEvent()
                }
            })
        }

        indicator.setViewPager(vpProductImage)
        imageAdapter?.registerAdapterDataObserver(indicator.adapterDataObserver)

        tvProviderPrice.paintFlags = tvProviderPrice.paintFlags or STRIKE_THRU_TEXT_FLAG
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            btnBack.id -> requireActivity().onBackPressed()
            ivPlus.id  -> viewModel.addItem()
            ivMinus.id -> viewModel.removeItem()
            btnTryAgain.id -> {
                groupError.visibility = View.GONE
                viewModel.getProductDetail(this@DetailFragment.sku)
            }
        }
    }

    private fun updateProductData(product: Product) {
        this.product = product
        tvTitleName.text = product.displayName
        if (product.price.sellPrice > 0.0) {
            tvTitlePrice.text = CurrencyUtils.formatCurrency(product.price.sellPrice)
        } else {
            tvTitlePrice.visibility = View.GONE
            groupCart.visibility = View.GONE
        }

        tvProductName.text = product.displayName
        tvProductCode.text = product.sku

        val sellingStatus = SellingStatus.from(product.status.sale)
        tvProductStatus.text = when (sellingStatus) {
            SellingStatus.STOP_SELLING -> getString(R.string.stop_selling)
            SellingStatus.OUT_OF_STOCK -> getString(R.string.out_of_stock)
            SellingStatus.SAMPLE       -> getString(R.string.sample)
            SellingStatus.SELLING      -> getString(R.string.available_product)
        }

        if (sellingStatus == SellingStatus.SELLING) {
            tvSellPrice.text = CurrencyUtils.formatCurrency(product.price.sellPrice)

            if (product.price.supplierSalePrice != product.price.sellPrice) {
                tvSellPrice.visibility = View.VISIBLE
                tvDiscount.visibility = View.VISIBLE

                tvProviderPrice.text = CurrencyUtils.formatCurrency(
                        product.price.supplierSalePrice,
                        withCurrency = false
                )
                var percent =
                        (product.price.supplierSalePrice - product.price.sellPrice) * 100 / product.price.supplierSalePrice
                if (percent < 1) {
                    percent = 1.0
                }
                tvDiscount.text = getString(R.string.format_discount_percent, percent.toInt())
            } else {
                tvProviderPrice.visibility = View.GONE
                tvDiscount.visibility = View.GONE
            }
        } else {
            groupCart.visibility = View.GONE
            tvSellPrice.visibility = View.GONE
            tvProviderPrice.visibility = View.GONE
            tvDiscount.visibility = View.GONE
        }

        imageAdapter?.setData(product.images)
        viewModel.startChangeSlideEvent()

        initProductDetail(product)
    }

    private fun initProductDetail(product: Product) {
//        vpProductDetail.apply {
//            offscreenPageLimit = 2
//            adapter = ProductDetailPagerAdapter(this@DetailFragment, Gson().toJson(product))
//            val params = layoutParams as ConstraintLayout.LayoutParams
//            params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
//            layoutParams = params
//        }

//        TabLayoutMediator(tlDetail, vpProductDetail, TabLayoutMediator.OnConfigureTabCallback { tab, position ->
//            tab.text = when (position) {
//                0    -> getString(R.string.product_description)
//                1    -> getString(R.string.technical_spec)
//                else -> getString(R.string.price_comparison)
//            }
//        }).attach()

        tlDetail.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                Timber.e("select tab at position = ${tab.position}")
                val fragment = when (tab.position) {
                    0    -> DescriptionFragment.getInstance(Gson().toJson(product))
                    1    -> SpecFragment.getInstance(Gson().toJson(product))
                    else -> PriceComparisonFragment()
                }
                replaceFragment(fragment, frameDetail.id)
            }
        })

        replaceFragment(DescriptionFragment.getInstance(Gson().toJson(product)), frameDetail.id)
    }

    companion object {
        const val PRODUCT_EXTRA = "product_extra"
    }
}