package com.android.productdiscovery.ui.listing

import android.os.Bundle
import com.android.productdiscovery.R
import com.android.productdiscovery.ui.base.BaseActivity
import com.android.productdiscovery.utils.extension.applyFullScreenConfig
import com.android.productdiscovery.utils.extension.findOrCreateViewFragment
import com.android.productdiscovery.utils.extension.replaceFragmentInActivity
import kotlinx.android.synthetic.main.activity_common_layout.*

/**
 * @author ThanhDT
 * @since 2019-07-28
 */
class ListingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyFullScreenConfig()

        setContentView(R.layout.activity_common_layout)

        replaceFragmentInActivity(findOrCreateViewFragment(frameContent.id, ListingFragment::class.java), frameContent.id)
    }
}