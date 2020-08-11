package com.guerra.enrico.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.guerra.enrico.base.extensions.exhaustive
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.base_android.widget.SnackbarBuilder
import com.guerra.enrico.login.models.LoginState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {
  private val viewModel: LoginViewModel by activityViewModels()

  companion object {
    private const val REQUEST_CODE_SIGN_IN = 9003
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
  }

  private fun initView() {
    setupObservers()

    val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestServerAuthCode(BuildConfig.OAUTH2_CLIENT_ID)
      .build()
    val googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)

    signInButton.setOnClickListener {
      startActivityForResult(
        googleSignInClient.signInIntent,
        REQUEST_CODE_SIGN_IN
      )
    }
  }

  private fun setupObservers() {
    observe(viewModel.viewState) { state ->
      when (state) {
        LoginState.Login,
        LoginState.Complete -> {
        }

        LoginState.Sync -> findNavController().navigate(R.id.syncFragment)
        is LoginState.Error -> {
          showSnackbar(
            SnackbarBuilder()
              .message(state.exception.message ?: getString(R.string.error_google_sign_in))
          )
        }
      }.exhaustive
    }

    observeLoading(viewModel.loading)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == REQUEST_CODE_SIGN_IN) {
      handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data))
    }
  }

  private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
    try {
      val account = task.getResult(ApiException::class.java)
      viewModel.onCodeReceived(account?.serverAuthCode ?: "")
    } catch (ex: ApiException) {
      showSnackbar(SnackbarBuilder().message(R.string.error_google_sign_in))
    }
  }
}