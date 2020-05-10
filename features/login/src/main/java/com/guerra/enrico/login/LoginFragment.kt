package com.guerra.enrico.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.extensions.observe
import com.guerra.enrico.sera.BuildConfig
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.databinding.FragmentLoginBinding
import com.guerra.enrico.base_android.arch.BaseFragment
import javax.inject.Inject

/**
 * Created by enrico
 * on 23/12/2019.
 */

class LoginFragment : BaseFragment() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private val viewModel: LoginViewModel by activityViewModels { viewModelFactory }

  private lateinit var binding: FragmentLoginBinding

  companion object {
    private const val REQUEST_CODE_SIGN_IN = 9003

    fun newInstance() = LoginFragment()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentLoginBinding.inflate(inflater, container, false).apply {
      lifecycleOwner = viewLifecycleOwner
    }
    return binding.root
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
    binding.signInButton.setOnClickListener {
      startActivityForResult(googleSignInClient.signInIntent,
        REQUEST_CODE_SIGN_IN
      )
    }
    observe(viewModel.user) {
      when (it) {
        is Result.Loading -> showOverlayLoader()
        is Result.Success -> {
          hideOverlayLoader()
          gotoMainActivity()
        }
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

  private fun gotoMainActivity() {
    findNavController().navigate(LoginFragmentDirections.actionLoginToSync())
  }
}