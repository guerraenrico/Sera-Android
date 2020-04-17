package com.guerra.enrico.remote

import com.guerra.enrico.models.Session
import com.guerra.enrico.models.sync.SyncAction
import com.guerra.enrico.models.sync.SyncedEntity
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Task
import com.guerra.enrico.remote.response.AuthData
import com.guerra.enrico.remote.response.CallResult
import java.util.*

/**
 * Created by enrico
 * on 10/09/2018.
 */
interface RemoteDataManager {
  /* Sign In */

  suspend fun googleSignInCallback(code: String): CallResult<AuthData>

  suspend fun validateAccessToken(accessToken: String): CallResult<AuthData>

  suspend fun refreshAccessToken(accessToken: String): CallResult<Session>

  /* Categories */

  suspend fun getCategories(accessToken: String, from: Date?): CallResult<List<Category>>

  suspend fun searchCategory(accessToken: String, text: String): CallResult<List<Category>>

  suspend fun insertCategory(accessToken: String, category: Category): CallResult<Category>

  suspend fun deleteCategory(accessToken: String, id: String): CallResult<Any>

  /* Tasks */

  suspend fun getTasks(accessToken: String, from: Date?): CallResult<List<Task>>

  suspend fun insertTask(accessToken: String, task: Task): CallResult<Task>

  suspend fun deleteTask(accessToken: String, id: String): CallResult<Any>

  suspend fun updateTask(accessToken: String, task: Task): CallResult<Task>

  suspend fun toggleCompleteTask(accessToken: String, task: Task): CallResult<Task>

  /* Sync */

  /**
   * Sync actions saved and fetch all entities that need to be updated
   * @param accessToken session token
   * @param lastSync if null read all entities otherwise all entities that have been updated after this date
   * @param syncActions list of actions to sync
   * @return list of synced entities that have to be saved in local
   */
  suspend fun sync(
    accessToken: String,
    lastSync: Date?,
    syncActions: List<SyncAction>
  ): CallResult<List<SyncedEntity>>
}