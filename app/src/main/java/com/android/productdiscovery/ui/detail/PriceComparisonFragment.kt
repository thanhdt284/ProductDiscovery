package com.android.productdiscovery.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.productdiscovery.R
import com.android.productdiscovery.ui.base.BaseFragment

/**
 * @author ThanhDT
 * @since 2019-08-01
 */
class PriceComparisonFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_price_comparison, container, false)
    }

}