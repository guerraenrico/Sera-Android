package com.guerra.enrico.sera.data.remote

import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Session
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.models.User
import com.guerra.enrico.sera.data.remote.request.AuthRequestParams
import com.guerra.enrico.sera.data.remote.request.CategoryParams
import com.guerra.enrico.sera.data.remote.request.TaskParams
import com.guerra.enrico.sera.data.remote.request.AccessTokenParams
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by enrico
 * on 02/06/2018.
 */
@Singleton
class RemoteDataManagerImpl @Inject constructor(
       private val api: Api
) : RemoteDataManager{

    /* Sign In */
    override fun googleSignInCallback(code: String): Single<ApiResponse<User>> {
        return api.googleSignInCallback(AuthRequestParams(code))
    }

    override fun validateAccessToken(accessToken: String): Single<ApiResponse<User>> {
        return api.validateAccessToken(AccessTokenParams(accessToken))
    }

    override fun refreshAccessToken(accessToken: String): Single<ApiResponse<Session>> {
        return api.refreshAccessToken(AccessTokenParams(accessToken))
    }

    /* Categories */

    override fun getCategories(
            accessToken: String,
            limit: Int,
            skip: Int
    ): Single<ApiResponse<List<Category>>> {
        return api.getCategories(accessToken, limit, skip)
    }

    override fun searchCategory(accessToken: String, text: String): Single<ApiResponse<List<Category>>> {
        return  api.searchCategory(accessToken, text)
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
    ): Single<ApiResponse<List<Task>>> {
        return api.getTasks(
                accessToken,
                (if (categoriesId.isNotEmpty()) categoriesId else listOf("")).joinToString().replace("\\s".toRegex(), ""),
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