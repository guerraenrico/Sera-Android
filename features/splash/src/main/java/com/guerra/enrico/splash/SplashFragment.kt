package com.guerra.enrico.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.guerra.enrico.base.extensions.exhaustive
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class SplashFragment : BaseFragment() {

  private val viewModel: SplashViewModel by viewModels()

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
    observe(viewModel.viewState) { state ->
      when (state) {
        SplashState.Idle -> {}
        SplashState.Complete -> gotoMain()
      }.exhaustive
    }
  }

  private fun gotoMain() {
    val uri = navigator.getUriMain()
    findNavController().navigate(uri)
  }
}