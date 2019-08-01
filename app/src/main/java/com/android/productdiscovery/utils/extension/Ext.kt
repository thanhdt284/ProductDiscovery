package com.android.productdiscovery.utils.extension

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.android.productdiscovery.app.App
import com.android.productdiscovery.app.ViewModelFactory
import com.android.productdiscovery.domain.remote.pojo.response.Error
import com.android.productdiscovery.utils.DisplayUtils
import com.android.productdiscovery.utils.RetrofitDisposable
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author ThanhDT
 * @since 2019-07-28
 */
fun Fragment.showToast(@StringRes resId: Int) {
    Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(message: String) {
    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
}

fun Context.toast(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun Fragment.replaceFragment(fragment: Fragment, frameId: Int) {
    childFragmentManager.transact {
        replace(frameId, fragment, fragment.javaClass.name)
    }
}

fun Fragment.showFragment(fragment: Fragment, visible: Boolean) {
    childFragmentManager.transact {
        if (visible) show(fragment) else hide(fragment)
    }
}

fun Fragment.findOrCreateViewFragment(@IdRes id: Int, clazz: Class<out Fragment>): Fragment =
    childFragmentManager.findFragmentById(id) ?: clazz.newInstance()

fun Fragment.hideKeyboard() {
    val imm =
        activity?.application?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    activity?.let {
        var view = it.currentFocus
        if (view == null) {
            view = View(it)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Fragment.showKeyboard() {
    activity?.currentFocus?.let {
        val imm =
            activity?.application?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(it, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

fun Fragment.adjustTopMargin(view: View) {
    val layoutParams = (view.layoutParams as ConstraintLayout.LayoutParams).apply {
        this@adjustTopMargin.context?.let {
            topMargin += DisplayUtils.getStatusBarHeight(it)
        }
    }
    view.layoutParams = layoutParams
}

/**
 * Converts an intent into a [Bundle] suitable for use as fragment arguments.
 */
fun intentToFragmentArguments(intent: Intent?): Bundle {
    val arguments = Bundle()
    if (intent == null) {
        return arguments
    }

    intent.data?.let {
        arguments.putParcelable("_uri", it)
    }

    intent.extras?.let {
        arguments.putAll(intent.extras)
    }

    return arguments
}

fun <T : ViewModel> Fragment.obtainViewModel(
    viewModelClass: Class<T>,
    activityObserver: Boolean = true
) =
    if (activityObserver) ViewModelProviders.of(
        requireActivity(),
        ViewModelFactory(requireActivity().application as App)
    ).get(viewModelClass)
    else ViewModelProviders.of(this, ViewModelFactory(requireActivity().application as App)).get(
        viewModelClass
    )

fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.action()
    editor.apply()
}

/**
 * Check if given string is a valid email address
 */
fun String.isEmail(): Boolean {
    return if (isNullOrEmpty()) {
        false
    } else {
        Patterns.EMAIL_ADDRESS.matcher(this).find()
    }
}

/**
 * Extension function that helps to use lambda expression for retrofit disposable using higher-order
 * function
 */
fun <T> Observable<T>.subscribeWith(onNext: (t: T) -> Unit,
                                    onHttpError: (error: Error) -> Unit = {},
                                    onNetworkError: (throwable: Throwable) -> Unit = {},
                                    doOnError: () -> Unit = {}): Disposable {

    return subscribeWith(object : RetrofitDisposable<T>() {
        override fun onNext(t: T) {
            onNext(t)
        }

        override fun onHttpError(error: Error) {
            onHttpError(error)
        }

        override fun onNetworkError(e: Throwable) {
            onNetworkError(e)
        }

        override fun doOnError() {
            doOnError()
        }
    })
}

fun <T> Observable<T>.observeOnMain(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.observeOnMain(): Flowable<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun Completable.observeOnMain(): Completable {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.observeOnMain(): Single<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}