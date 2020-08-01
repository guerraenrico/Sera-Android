package com.guerra.enrico.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.extensions.observe
import com.guerra.enrico.base.extensions.observeEvent
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.login.models.Step
import com.guerra.enrico.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by enrico
 * on 05/04/2020.
 */
@AndroidEntryPoint
internal class SyncFragment : BaseFragment() {
  @Inject
  lateinit var navigator: Navigator

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

    observeEvent(viewModel.step) {
      when (it) {
        Step.LOGIN -> findNavController().navigate(R.id.loginFragment)
        Step.SYNC -> findNavController().navigate(R.id.syncFragment)
        Step.COMPLETE -> {
          val uri = navigator.getUriTodos()
          findNavController().navigate(uri)
        }
      }
    }

  }
}