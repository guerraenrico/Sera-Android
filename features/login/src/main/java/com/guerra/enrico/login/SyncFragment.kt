package com.guerra.enrico.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.extensions.observe
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.login.databinding.FragmentLoginSyncBinding
import com.guerra.enrico.navigation.Navigator
import javax.inject.Inject

/**
 * Created by enrico
 * on 05/04/2020.
 */
internal class SyncFragment : BaseFragment() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private val viewModel: LoginViewModel by activityViewModels { viewModelFactory }

  @Inject
  lateinit var navigator: Navigator

  private lateinit var binding: FragmentLoginSyncBinding

  companion object {
    fun newInstance() = SyncFragment()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentLoginSyncBinding.inflate(inflater, container, false).apply {
      lifecycleOwner = viewLifecycleOwner
    }
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    observe(viewModel.sync) {
      when (it) {
        is Result.Loading -> return@observe
        is Result.Success -> gotoMainActivity()
        is Result.Error -> showSnackbar(
          it.exception.message ?: resources.getString(R.string.error_google_signin)
        )
      }
    }
    viewModel.startSync()
  }

  private fun gotoMainActivity() {
    navigator.startMainActivity(requireActivity())
    requireActivity().finish()
  }
}