package com.guerra.enrico.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.extensions.observe
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 23/12/2019.
 */
@AndroidEntryPoint
class LoginFragment : BaseFragment() {
  private val viewModel: LoginViewModel by activityViewModels()

  @Inject
  lateinit var navigator: Navigator

  companion object {
    private const val REQUEST_CODE_SIGN_IN = 9003

    fun newInstance() = LoginFragment()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_login, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
  }

  private fun initView() {
    val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestServerAuthCode(BuildConfig.OAUTH2_CLIENT_ID)
      .build()
    val googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)
    sign_in_button.setOnClickListener {
      startActivityForResult(
        googleSignInClient.signInIntent,
        REQUEST_CODE_SIGN_IN
      )
    }
    observe(viewModel.user) {
      when (it) {
        is Result.Loading -> showOverlayLoader()
        is Result.Success -> hideOverlayLoader()
        is Result.Error -> {
          hideOverlayLoader()
          showSnackbar(it.exception.message ?: resources.getString(R.string.error_google_signin))
        }
      }

    }
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
      showSnackbar(R.string.error_google_signin)
    }
  }
}