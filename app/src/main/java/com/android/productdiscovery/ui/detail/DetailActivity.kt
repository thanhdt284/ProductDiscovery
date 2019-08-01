package com.android.productdiscovery.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.productdiscovery.R
import com.android.productdiscovery.ui.base.BaseActivity
import com.android.productdiscovery.utils.extension.applyFullScreenConfig
import com.android.productdiscovery.utils.extension.intentToFragmentArguments
import com.android.productdiscovery.utils.extension.replaceFragmentInActivity
import kotlinx.android.synthetic.main.activity_common_layout.*

/**
 * @author ThanhDT
 * @since 2019-08-01
 */
class DetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyFullScreenConfig()

        setContentView(R.layout.activity_common_layout)

        replaceFragmentInActivity(findOrCreateViewFragment(), frameContent.id)
    }

    private fun findOrCreateViewFragment() =
            supportFragmentManager.findFragmentById(R.id.frameContent) ?:
            DetailFragment().apply { arguments = intentToFragmentArguments(intent) }

    companion object {
        const val SKU_EXTRA = "sku_extra"

        fun startActivity(context: Context, sku: String) {
            context.startActivity(Intent(context, DetailActivity::class.java).apply {
                putExtra(SKU_EXTRA, sku)
            })
        }
    }
}