package com.guerra.enrico.sera.di.module

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.guerra.enrico.sera.BuildConfig
import com.guerra.enrico.base.util.ConnectionHelper
import com.guerra.enrico.data.exceptions.ConnectionException
import com.guerra.enrico.data.remote.Api
import dagger.Module
import dagger.Provides
import okhttp3.*
import retrofit2.Retrofit
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
  fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

  @Provides
  fun provideRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit = Retrofit.Builder()
          .baseUrl(BuildConfig.ApiBaseUri)
          .client(okHttpClient)
          .addConverterFactory(gsonConverterFactory)
          .build()

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
  fun provideInterceptor(context: Context): Interceptor = Interceptor { chain ->
    val request = chain.request()
    if (!ConnectionHelper.isInternetConnectionAvailable(context)) {
      throw ConnectionException.internetConnectionNotAvailable()
    }
    try {
      chain.proceed(request)
    } catch (e: IOException) {
      if (e is SocketTimeoutException) {
        throw ConnectionException.operationTimeout()
      }
      throw e
    }
  }
}