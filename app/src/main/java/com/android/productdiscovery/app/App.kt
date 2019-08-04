package com.android.productdiscovery.app

import androidx.multidex.MultiDexApplication
import com.android.productdiscovery.BuildConfig.DEBUG
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber


/**
 * @author ThanhDT
 * @since 2019-07-28
 */
class App : MultiDexApplication() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        Timber.e("App onCreate")

        initTimber()

        if (DEBUG) {
            initLeakCanary()
            initStetho()
        }

        initDependency()
    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }

    private fun initDependency() {
        Timber.e("App initDependency")
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

        appComponent.inject(this)
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }

    private fun initTimber() {
        if (DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return super.createStackElementTag(element) + " : " + element.lineNumber
                }
            })
        }
    }
}