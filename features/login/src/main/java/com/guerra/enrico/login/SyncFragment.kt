package com.guerra.enrico.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.guerra.enrico.base.extensions.exhaustive
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.login.models.LoginState
import com.guerra.enrico.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by enrico
 * on 05/04/2020.
 */
@AndroidEntryPoint
internal class SyncFragment : BaseFragment(R.layout.fragment_login_sync) {
  @Inject
  lateinit var navigator: Navigator

  private val viewModel: LoginViewModel by activityViewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupObservers()
  }

  private fun setupObservers() {
    observeLoading(viewModel.loading)
    observe(viewModel.viewState) { state ->
      when (state) {
        LoginState.Sync -> {
        }
        LoginState.Login -> findNavController().navigate(R.id.loginFragment)
        LoginState.Complete -> {
          val uri = navigator.getUriMain()
          findNavController().navigate(uri)
        }
        is LoginState.Error -> {
          hideOverlayLoader()
          showSnackbar(state.exception.message ?: resources.getString(R.string.exception_unknown))
        }
      }.exhaustive
    }
  }

}