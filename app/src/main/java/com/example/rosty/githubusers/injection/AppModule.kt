package com.example.rosty.githubusers.injection

import android.content.Context

import com.example.rosty.githubusers.RxSchedulers
import com.example.rosty.githubusers.data.DataSource
import com.example.rosty.githubusers.data.DataSourceImpl
import com.example.rosty.githubusers.data.remote.ApiService
import com.example.rosty.githubusers.data.temp.TempDataManager
import com.example.rosty.githubusers.data.temp.TempDataManagerImpl

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber


@Module class AppModule(private val appContext: Context) {

    @Singleton
    @Provides
    fun provideAppContext() = appContext

    @Singleton
    @Provides
    fun provideDataSource(apiService:      ApiService,
                          tempDataManager: TempDataManager,
                          rxSchedulers:    RxSchedulers): DataSource {

        return DataSourceImpl(apiService, tempDataManager, rxSchedulers)
    }

    @Singleton
    @Provides
    fun provideApiService(): ApiService {

        val loggingInterceptor = HttpLoggingInterceptor( { Timber.d(it) })
                .setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

        val retrofit = Retrofit.Builder().client(okHttpClient)
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideATempDataManager(): TempDataManager {
        return TempDataManagerImpl()
    }

    @Singleton
    @Provides
    fun provideRxSchedulers(): RxSchedulers {

        return object : RxSchedulers {

            override fun main(): Scheduler { return AndroidSchedulers.mainThread() }

            override fun io(): Scheduler { return Schedulers.io() }
        }
    }
}
