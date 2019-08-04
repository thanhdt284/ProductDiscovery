package com.android.productdiscovery.ui.widget

import androidx.annotation.StringRes
import androidx.fragment.app.FragmentManager
import com.android.productdiscovery.R

/**
 * @author ThanhDT
 * @since 2019-07-28
 */
class ProgressDialogController
constructor(private val fragmentManager: FragmentManager) {

    private var progressFragment: ProgressDialogFragment

    init {
        progressFragment = ProgressDialogFragment.newInstance(R.string.message_processing)
    }

    fun setMessageResource(@StringRes resId: Int) {
        if (progressFragment.isVisible) {
            progressFragment.dismiss()
        }

        progressFragment = ProgressDialogFragment.newInstance(resId)
    }

    fun startProgress() {
        if (!progressFragment.isAdded)
            progressFragment.show(fragmentManager, "progress")
    }

    fun finishProgress() {
        if (!progressFragment.isDetached && !progressFragment.isStateSaved && progressFragment.isAdded)
            progressFragment.dismiss()
    }
}