package com.android.productdiscovery.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.android.productdiscovery.ui.widget.ProgressDialogController

/**
 * @author ThanhDT
 * @since 2019-07-28
 */
open class BaseFragment : Fragment() {
    private var progressController: ProgressDialogController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        activity?.let {
            progressController = ProgressDialogController(it.supportFragmentManager)
        }
    }

    fun showProgress() {
        progressController?.startProgress()
    }

    fun hideProgress() {
        progressController?.finishProgress()
    }
}