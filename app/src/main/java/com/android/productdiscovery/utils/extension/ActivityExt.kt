package com.android.productdiscovery.utils.extension

import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.android.productdiscovery.app.App
import com.android.productdiscovery.app.ViewModelFactory
import com.android.productdiscovery.utils.DisplayUtils

/**
 * @author ThanhDT
 * @since 2019-07-28
 */
/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, frameId: Int, isApplyAnim: Boolean = false) {
    supportFragmentManager.transact {
        if (isApplyAnim) {
            setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        replace(frameId, fragment, fragment.javaClass.name)
    }
}

fun AppCompatActivity.removeFragmentInActivity(fragment: Fragment) {
    supportFragmentManager.transact {
        remove(fragment)
    }
}

fun AppCompatActivity.applyFullScreenConfig() {
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
}

fun AppCompatActivity.applySecureScreen() {
    window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
}

fun AppCompatActivity.adjustTopMargin(view: View) {
    val layoutParams = (view.layoutParams as ConstraintLayout.LayoutParams).apply {
        topMargin += DisplayUtils.getStatusBarHeight(this@adjustTopMargin)
    }
    view.layoutParams = layoutParams
}

fun View.adjustTopMargin(){
    val layoutParams = (this.layoutParams as ConstraintLayout.LayoutParams).apply {
        topMargin += DisplayUtils.getStatusBarHeight(context)
    }
    this.layoutParams = layoutParams
}

/**
 * Runs a FragmentTransaction, then calls commit().
 */
inline fun androidx.fragment.app.FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commitNowAllowingStateLoss()
}

fun AppCompatActivity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(findViewById<View>(android.R.id.content)?.windowToken, 0)
}

fun AppCompatActivity.showKeyboard() {
    currentFocus?.let {
        val imm = application?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(it, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

fun AppCompatActivity.showToast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, length).show()
}

fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
        ViewModelProviders.of(this, ViewModelFactory(application as App)).get(viewModelClass)

fun AppCompatActivity.findOrCreateViewFragment(@IdRes id: Int, clazz: Class<out Fragment>): Fragment =
        supportFragmentManager.findFragmentById(id) ?: clazz.newInstance()

fun AppCompatActivity.showFragmentToActivity(fragment: Fragment, visible: Boolean) {
    supportFragmentManager.transact {
        if (visible) show(fragment) else hide(fragment)
    }
}

fun <T : Fragment> AppCompatActivity.findOrCreateFragmentByTag(tag: String, clazz: Class<T>): Fragment {
    var frag = supportFragmentManager.findFragmentByTag(tag)
    if (frag == null) {
        try {
            frag = clazz.newInstance()
        } catch (e: InstantiationException) {
            e.printStackTrace()
            return Fragment()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            return Fragment()
        }

    }
    return frag!!
}
