package com.android.productdiscovery.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnPreDraw
import androidx.transition.TransitionManager
import com.android.productdiscovery.R
import com.android.productdiscovery.domain.remote.pojo.response.Product
import com.android.productdiscovery.ui.base.BaseFragment
import com.android.productdiscovery.ui.detail.DetailFragment.Companion.PRODUCT_EXTRA
import com.android.productdiscovery.utils.DisplayUtils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_description.*
import timber.log.Timber

/**
 * @author ThanhDT
 * @since 2019-08-01
 */
class DescriptionFragment : BaseFragment() {

    private var isExpanded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_description, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var description = ""
        arguments?.getString(PRODUCT_EXTRA, "")?.let {
            val product = Gson().fromJson(it, Product::class.java)
            description = product.seoInfo.shortDescription
        }
        Timber.e(description)
        Timber.e("html result = ${DisplayUtils.fromHtml(description)}")

        tvDescription.text = DisplayUtils.fromHtml(description)

        val defaultHeight = resources.getDimensionPixelSize(R.dimen.detail_collapsing_height)
        tvDescription.doOnPreDraw {

            Timber.e("default min height = ${resources.getDimensionPixelSize(R.dimen.detail_collapsing_height)}")
            Timber.e("height = ${tvDescription.height}")
            Timber.e("measuredHeight = ${tvDescription.measuredHeight}")
            Timber.e("root measuredHeight = ${ctnRoot.measuredHeight}")
            Timber.e("tv text = ${tvDescription.text}")

            if (tvDescription.height > defaultHeight) {
                groupExpand.visibility = View.VISIBLE
                vOverlay.visibility = View.VISIBLE

                val params = tvDescription.layoutParams
                params.height = defaultHeight
                tvDescription.layoutParams = params
            } else {
                groupExpand.visibility = View.GONE
                vOverlay.visibility = View.GONE
            }
        }

        vShowMore.setOnClickListener {
            TransitionManager.beginDelayedTransition(ctnRoot)

            val params = tvDescription.layoutParams

            if (isExpanded) {
                tvShowMore.text = getString(R.string.display_more)
                ivArrow.rotation = 180f
                vOverlay.visibility = View.VISIBLE

                params.height = defaultHeight
            } else {
                tvShowMore.text = getString(R.string.collapse)
                ivArrow.rotation = 0f
                vOverlay.visibility = View.GONE

                params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            }

            tvDescription.layoutParams = params

            isExpanded = !isExpanded
        }
    }

    companion object {
        fun getInstance(product: String) = DescriptionFragment().apply {
            arguments = Bundle().apply {
                putString(PRODUCT_EXTRA, product)
            }
        }
    }
}