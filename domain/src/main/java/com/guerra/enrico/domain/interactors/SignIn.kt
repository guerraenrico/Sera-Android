package com.guerra.enrico.domain.interactors

import com.guerra.enrico.data.Result
import com.guerra.enrico.data.models.User
import com.guerra.enrico.data.repo.auth.AuthRepository
import com.guerra.enrico.domain.InteractorRx
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/11/2019.
 */
class SignIn @Inject constructor(
        private val authRepository: AuthRepository,
        private val syncTasksAndCategories: SyncTasksAndCategories
) : InteractorRx<String, Single<Result<User>>>() {
  override fun doWork(params: String): Single<Result<User>> =
          authRepository.googleSignInCallback(params)
                  .doAfterSuccess { syncTasksAndCategories.execute(Unit) }
}