package com.guerra.enrico.domain.interactors

import com.guerra.enrico.data.Result
import com.guerra.enrico.data.models.User
import com.guerra.enrico.data.repo.auth.AuthRepository
import com.guerra.enrico.domain.Interactor
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/11/2019.
 */
class ValidateToken @Inject constructor(
        private val authRepository: AuthRepository,
        private val syncTasksAndCategories: SyncTasksAndCategories
) : Interactor<Unit, Single<Result<User>>>() {
  override fun doWork(params: Unit): Single<Result<User>> =
          authRepository.validateAccessToken()
                  .retryWhen {
                    authRepository.refreshTokenIfNotAuthorized(it)
                  }
                  .doAfterSuccess { syncTasksAndCategories.execute(Unit) }
}