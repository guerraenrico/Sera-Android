package com.guerra.enrico.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.succeeded
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by enrico
 * on 23/12/2019.
 */
@AndroidEntryPoint
internal class SplashFragment : BaseFragment() {
  private val viewModel: SplashViewModel by activityViewModels()

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
        gotoTodos()
      }
      if (userResult is Result.Error) {
        gotoLogin()
      }
    })
  }

  private fun gotoTodos() {
    val uri = navigator.getUriTodos()
    findNavController().navigate(uri)
    requireActivity().finish()
  }

  private fun gotoLogin() {
    val uri = navigator.getUriLogin()
    findNavController().navigate(uri)
    requireActivity().finish()
  }
}