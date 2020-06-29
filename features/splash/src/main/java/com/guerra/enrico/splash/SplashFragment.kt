package com.guerra.enrico.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.succeeded
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.login.LoginNavigationRoutes
import com.guerra.enrico.main.MainNavigationRoutes
import com.guerra.enrico.navigation.Navigator
import javax.inject.Inject

/**
 * Created by enrico
 * on 23/12/2019.
 */
internal class SplashFragment : BaseFragment() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private val viewModel: SplashViewModel by activityViewModels { viewModelFactory }

  @Inject
  lateinit var navigator: Navigator

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
    viewModel.validationAccessTokenResult.observe(viewLifecycleOwner, Observer { userResult ->
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
    val target = MainNavigationRoutes.Main.buildTarget()
    navigator.startActivity(requireActivity(), target)
    requireActivity().finish()
  }

  private fun gotoLoginActivity() {
    val target = LoginNavigationRoutes.Login.buildTarget()
    navigator.startActivity(requireActivity(), target)
    requireActivity().finish()
  }
}