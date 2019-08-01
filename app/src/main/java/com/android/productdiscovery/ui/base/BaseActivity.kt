package com.android.productdiscovery.ui.base

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import com.android.productdiscovery.utils.extension.hideKeyboard
import org.greenrobot.eventbus.EventBus

/**
 * @author Steve
 * @since 08-Sep-17
 */
@SuppressLint("Registered")
abstract class BaseActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
//        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
//        EventBus.getDefault().unregister(this)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        hideKeyboard()
//        onBackPressed()
//        return true
//    }

    override fun onBackPressed() {
        hideKeyboard()
        super.onBackPressed()
    }

}