package com.guerra.enrico.sera.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.guerra.enrico.base.util.activityViewModelProvider
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.data.Result
import com.guerra.enrico.sera.data.succeeded
import com.guerra.enrico.sera.ui.base.BaseFragment
import javax.inject.Inject

/**
 * Created by enrico
 * on 23/12/2019.
 */
class SplashFragment : BaseFragment() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private lateinit var viewModel: SplashViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_splash, container, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = activityViewModelProvider(viewModelFactory)
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
//    findNavController().navigate(R.id.main_activity)

    findNavController().navigate(R.id.login_activity)

    requireActivity().finish()
  }

  private fun gotoLoginActivity() {
    findNavController().navigate(R.id.login_activity)
    requireActivity().finish()
  }
}