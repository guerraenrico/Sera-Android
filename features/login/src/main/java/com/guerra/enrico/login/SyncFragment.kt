package com.guerra.enrico.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.extensions.observe
import com.guerra.enrico.base_android.arch.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by enrico
 * on 05/04/2020.
 */
@AndroidEntryPoint
internal class SyncFragment : BaseFragment() {
  private val viewModel: LoginViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_login_sync, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    observe(viewModel.sync) {
      when (it) {
        is Result.Loading,
        is Result.Success -> return@observe
        is Result.Error -> showSnackbar(
          it.exception.message ?: resources.getString(R.string.error_google_signin)
        )
      }
    }

  }
}