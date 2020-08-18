package com.guerra.enrico.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialFadeThrough
import com.guerra.enrico.base.extensions.exhaustive
import com.guerra.enrico.base.extensions.lazyFast
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.base_android.extensions.applyWindowInsets
import com.guerra.enrico.components.recyclerview.decorators.VerticalDividerItemDecoration
import com.guerra.enrico.settings.models.SettingEvent
import com.guerra.enrico.settings.models.SettingsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings.*

@AndroidEntryPoint
internal class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
  private val viewModel: SettingsViewModel by viewModels()

  private val settingAdapter: SettingAdapter by lazyFast {
    SettingAdapter(viewModel)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enterTransition = MaterialFadeThrough()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
  }

  private fun initView() {
    toolbar_title.text = getString(R.string.title_settings)
    toolbar.applyWindowInsets(top = true)

    initRecyclerView()
    setupObservers()
  }

  private fun initRecyclerView() {
    val linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    val defaultItemAnimator = DefaultItemAnimator().apply { supportsChangeAnimations = false }
    recycler_view_settings.apply {
      layoutManager = linearLayoutManager
      adapter = settingAdapter
      itemAnimator = defaultItemAnimator
      addItemDecoration(VerticalDividerItemDecoration(context))
    }
  }

  private fun setupObservers() {
    observe(viewModel.viewState) { state ->
      when (state) {
        SettingsState.Idle -> {
        }
        is SettingsState.Items -> {
          val list = state.toOptions()
          settingAdapter.submitList(list)
        }
      }
    }

    observeEvent(viewModel.events) { event ->
      when (event) {
        is SettingEvent.EnableDarkMode -> toggleNightMode(event.enable)
      }.exhaustive
    }
  }

  private fun toggleNightMode(enabled: Boolean) {
    if (enabled) {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    } else {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
  }
}