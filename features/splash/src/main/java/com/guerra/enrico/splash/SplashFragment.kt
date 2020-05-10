package com.guerra.enrico.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.guerra.enrico.sera.R
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.succeeded
import com.guerra.enrico.base_android.arch.BaseFragment
import javax.inject.Inject

/**
 * Created by enrico
 * on 23/12/2019.
 */
class SplashFragment : BaseFragment() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private val viewModel: SplashViewModel by activityViewModels { viewModelFactory }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_splash, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
  }

  private fun initView() {
    viewModel.validationAccessTokenResult.observe(this, Observer { userResult ->
      if (userResult == null || userResult is Result.Loading) return@Observer
      if (userResult.succeeded) {
        gotoMainActivity()
      }
      if (userResult is Result.Error) {
        gotoLoginActivity()
      }
    })
  }

  private fun gotoMainActivity() {
    findNavController().navigate(R.id.main_activity)
    requireActivity().finish()
  }

  private fun gotoLoginActivity() {
    findNavController().navigate(R.id.login_activity)
    requireActivity().finish()
  }
}