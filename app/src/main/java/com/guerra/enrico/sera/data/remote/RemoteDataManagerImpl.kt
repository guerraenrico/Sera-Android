package com.guerra.enrico.sera.data.remote

import android.content.Context
import com.google.gson.GsonBuilder
import com.guerra.enrico.sera.BuildConfig
import com.guerra.enrico.sera.data.exceptions.OperationException
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.models.User
import com.guerra.enrico.sera.data.remote.request.AuthRequestParams
import com.guerra.enrico.sera.data.remote.request.CategoryParams
import com.guerra.enrico.sera.data.remote.request.TaskParams
import com.guerra.enrico.sera.data.remote.request.ValidateAccessTokenParams
import com.guerra.enrico.sera.util.ConnectionHelper
import io.reactivex.Flowable
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by enrico
 * on 02/06/2018.
 */
@Singleton
class RemoteDataManagerImpl @Inject constructor(
        context: Context
) : RemoteDataManager{
    private val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .setLenient()
            .create()

    private val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor {
                val request = it.request()
                if (!ConnectionHelper.isInternetConnectionAvailable(context)) {
                    throw OperationException.InternetConnectionUnavailable()
                }
                return@addNetworkInterceptor it.proceed(request)
            }
            .build()

    private val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.ApiBaseUri)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    private val api = retrofit.create(Api::class.java)

    /* Sign In */
    override fun googleSignInCallback(code: String): Single<ApiResponse<User>> {
        return api.googleSignInCallback(AuthRequestParams(code))
    }

    override fun validateAccessToken(accessToken: String): Single<ApiResponse<User>> {
        return api.validateAccessToken(ValidateAccessTokenParams(accessToken))
    }

    /* Categories */

    override fun getCategories(
            accessToken: String,
            limit: Int,
            skip: Int
    ): Flowable<ApiResponse<List<Category>>> {
        return api.getCategories(accessToken, limit, skip)
    }

    override fun insertCategory(accessToken: String, category: Category): Single<ApiResponse<Category>> {
        return api.insertCategory(
                accessToken,
                CategoryParams(category)
        )
    }

    override fun deleteCategory(accessToken: String, id: String): Single<ApiResponse<Any>> {
        return api.deleteCategory(accessToken, id)
    }

    /* Tasks */

    override fun getTasks(
            accessToken: String,
            categoriesId: List<String>,
            completed: Boolean,
            limit: Int,
            skip: Int
    ): Flowable<ApiResponse<List<Task>>> {
        return api.getTasks(
                accessToken,
                (if (categoriesId.isNotEmpty()) categoriesId else listOf("0")).joinToString().replace("\\s".toRegex(), ""),
                completed,
                limit,
                skip
        )
    }

    override fun insertTask(accessToken: String, task: Task): Single<ApiResponse<Task>> {
        return api.insertTask(
                accessToken,
                TaskParams(task)
        )
    }

    override fun deleteTask(accessToken: String, id: String): Single<ApiResponse<Any>> {
        return api.deleteTask(accessToken, id)
    }

    override fun updateTask(accessToken: String, task: Task): Single<ApiResponse<Task>> {
        return api.updateTask(
                accessToken,
                TaskParams(task)
        )
    }
}