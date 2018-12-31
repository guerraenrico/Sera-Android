package com.guerra.enrico.sera.di.module

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.guerra.enrico.sera.BuildConfig
import com.guerra.enrico.sera.data.exceptions.OperationException
import com.guerra.enrico.sera.util.ConnectionHelper
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by enrico
 * on 26/12/2018.
 */
@Module(includes = [AppModule::class])
class RetrofitModule {
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory, rxJava2CallAdapterFactory: RxJava2CallAdapterFactory) : Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.ApiBaseUri)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .build()

    @Provides
    fun provideRxCallAdapterFactory(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    @Provides
    fun provideGsonConerterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Provides
    fun provideGson() : Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .setLenient()
            .create()

    @Provides
    fun provideOkHttpClient(networkInterceptor: Interceptor) : OkHttpClient =  OkHttpClient.Builder()
                .addNetworkInterceptor(networkInterceptor)
                .build()

    @Provides
    fun provideNetworkInterceptor(context: Context): Interceptor =  Interceptor {
            val request = it.request()
            if (!ConnectionHelper.isInternetConnectionAvailable(context)) {
                throw OperationException.InternetConnectionUnavailable()
            }
            return@Interceptor it.proceed(request)
        }
}