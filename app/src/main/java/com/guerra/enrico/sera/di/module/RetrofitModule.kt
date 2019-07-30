package com.guerra.enrico.sera.di.module

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.guerra.enrico.sera.BuildConfig
import com.guerra.enrico.sera.data.exceptions.OperationException
import com.guerra.enrico.sera.data.remote.Api
import com.guerra.enrico.sera.util.ConnectionHelper
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

/**
 * Created by enrico
 * on 26/12/2018.
 */
@Module(includes = [AppModule::class])
class RetrofitModule {
  @Provides
  fun provideApi(retrofit: Retrofit) = retrofit.create(Api::class.java)

  @Provides
  fun provideRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory, rxJava2CallAdapterFactory: RxJava2CallAdapterFactory): Retrofit = Retrofit.Builder()
          .baseUrl(BuildConfig.ApiBaseUri)
          .client(okHttpClient)
          .addConverterFactory(gsonConverterFactory)
          .addCallAdapterFactory(rxJava2CallAdapterFactory)
          .build()

  @Provides
  fun provideRxCallAdapterFactory(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

  @Provides
  fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

  @Provides
  fun provideGson(): Gson = GsonBuilder()
          .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
          .setLenient()
          .create()

  @Provides
  fun provideOkHttpClient(networkInterceptor: Interceptor): OkHttpClient = OkHttpClient.Builder()
          .connectTimeout(15, TimeUnit.SECONDS)
          .readTimeout(15, TimeUnit.SECONDS)
          .writeTimeout(15, TimeUnit.SECONDS)
          .addInterceptor(networkInterceptor)
          .build()

  @Provides
  fun provideNetworkInterceptor(context: Context): Interceptor = Interceptor { chain ->
    val request = chain.request()
    if (!ConnectionHelper.isInternetConnectionAvailable(context)) {
      throw OperationException.internetConnectionUnavailable()
    }
    try {
      chain.proceed(request)
    } catch (e: IOException) {
      if (e is SocketTimeoutException) {
        throw OperationException.operationTimeout()
      }
      throw e
    }
  }
}