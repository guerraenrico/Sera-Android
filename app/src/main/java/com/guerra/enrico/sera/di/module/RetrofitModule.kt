package com.guerra.enrico.sera.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.guerra.enrico.sera.BuildConfig
import com.guerra.enrico.sera.data.remote.Api
import dagger.Module
import dagger.Provides
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
  fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
          .connectTimeout(15, TimeUnit.SECONDS)
          .readTimeout(15, TimeUnit.SECONDS)
          .writeTimeout(15, TimeUnit.SECONDS)
          .build()

//  @Provides
//  fun provideInterceptor(): Interceptor = Interceptor { chain ->
//    val request = chain.request()
//    try {
//      chain.proceed(request)
//    } catch (e: IOException) {
//      if (e is SocketTimeoutException) {
//        throw ConnectionException.operationTimeout()
//      }
//      if (e is UnknownHostException) {
//        throw ConnectionException.internetConnectionNotAvailable()
//      }
//      throw e
//    }
//  }
}