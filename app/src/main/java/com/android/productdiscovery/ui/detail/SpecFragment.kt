package com.android.productdiscovery.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.android.productdiscovery.R
import com.android.productdiscovery.domain.remote.pojo.response.AttributeGroup
import com.android.productdiscovery.domain.remote.pojo.response.Product
import com.android.productdiscovery.ui.base.BaseFragment
import com.android.productdiscovery.ui.detail.DetailFragment.Companion.PRODUCT_EXTRA
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_spec.*
import timber.log.Timber

/**
 * @author ThanhDT
 * @since 2019-08-01
 */
class SpecFragment : BaseFragment() {

    private var isExpanded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_spec, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val listSpec = mutableListOf<AttributeGroup>()

        arguments?.getString(PRODUCT_EXTRA, "")?.let {
            val product = Gson().fromJson(it, Product::class.java)
            Timber.e("spec = ${product.attributeGroups}")
            listSpec.addAll(product.attributeGroups)
        }

        rvSpec.apply {
            adapter = SpecAdapter(listSpec)
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
        }

        val defaultHeight = resources.getDimensionPixelSize(R.dimen.detail_collapsing_height)
        var maxHeight = 0

        rvSpec.doOnPreDraw {
//            Timber.e("default min height = ${resources.getDimensionPixelSize(R.dimen.detail_collapsing_height)}")
//            Timber.e("height = ${rvSpec.height}")
//            Timber.e("measuredHeight = ${rvSpec.measuredHeight}")
//            Timber.e("root measuredHeight = ${ctnRoot.measuredHeight}")
            maxHeight = rvSpec.height

            if (rvSpec.height > defaultHeight) {
                groupExpand.visibility = View.VISIBLE
                vOverlay.visibility = View.VISIBLE

                val params = rvSpec.layoutParams
                params.height = defaultHeight
                rvSpec.layoutParams = params
            } else {
                groupExpand.visibility = View.GONE
                vOverlay.visibility = View.GONE
            }
        }

        vShowMore.setOnClickListener {
            TransitionManager.beginDelayedTransition(ctnRoot)

            val params = rvSpec.layoutParams
            if (isExpanded) {
                tvShowMore.text = getString(R.string.display_more)
                ivArrow.rotation = 180f
                vOverlay.visibility = View.VISIBLE
                params.height = defaultHeight
            } else {
                tvShowMore.text = getString(R.string.collapse)
                ivArrow.rotation = 0f
                vOverlay.visibility = View.GONE
                params.height = maxHeight
            }
            rvSpec.layoutParams = params

            isExpanded = !isExpanded
        }
    }

    companion object {
        fun getInstance(product: String) = SpecFragment().apply {
            arguments = Bundle().apply {
                putString(PRODUCT_EXTRA, product)
            }
        }
    }
}