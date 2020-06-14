package com.guerra.enrico.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.succeeded
import com.guerra.enrico.base_android.arch.BaseFragment
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
    findNavController().navigate(R.id.action_to_main)
    requireActivity().finish()
  }

  private fun gotoLoginActivity() {
    findNavController().navigate(R.id.action_to_login)
    requireActivity().finish()
  }
}