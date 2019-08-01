package com.android.productdiscovery.domain.remote.api

import android.app.Application
import com.android.productdiscovery.data.manager.UserManager
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author ThanhDT
 * @since 2019-07-28
 */
@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpCache(application: Application): Cache {
        val size: Long = 100 * 1024 * 1024 // 100Mb
        return Cache(application.cacheDir, size)
    }

    @Provides
    @Singleton
    @Named("logger")
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor { message ->
            Timber.i(message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    @Named("header")
    fun provideHeaderInterceptor(userManager: UserManager): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + userManager.accessToken)
                .method(original.method(), original.body())
                .build()

            return@Interceptor chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideAuthenticator(preferenceManager: UserManager): Authenticator {
        return Authenticator(preferenceManager)
    }

    @Provides
    @Singleton
    @Named("auth_okhttp")
    fun provideOkHttpClient(auth: Authenticator, cache: Cache,
                            @Named("logger") logger: Interceptor,
                            @Named("header") header: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(header)
                .addInterceptor(logger)
                .build()
    }

    @Provides
    @Singleton
    @Named("no_auth_okhttp")
    fun provideNoAuthOkHttpClient(cache: Cache, @Named("logger") logger: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(logger)
                .build()
    }

    @Provides
    @Singleton
    @Named("auth_retrofit")
    fun provideRetrofit(@Named("auth_okhttp") okHttpClient: OkHttpClient): Retrofit {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.excludeFieldsWithoutExposeAnnotation()

        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    @Named("no_auth_retrofit")
    fun provideNoAuthRetrofit(@Named("no_auth_okhttp") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun provideApiService(@Named("auth_retrofit") retrofit: Retrofit,
                          @Named("no_auth_retrofit") noAuthRetrofit: Retrofit,
                          auth: Authenticator
    ): ApiService {
        auth.apiService = noAuthRetrofit.create(ApiServiceNoAuth::class.java)

        return retrofit.create(ApiService::class.java)
    }


    companion object {
        const val TIME_OUT: Long = 30
        const val BASE_URL = "https://listing-stg.services.teko.vn/api/"
    }
}