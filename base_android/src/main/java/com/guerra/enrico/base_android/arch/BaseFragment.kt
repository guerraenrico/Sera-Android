package com.guerra.enrico.base_android.arch

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.guerra.enrico.base.Event
import com.guerra.enrico.base_android.R
import com.guerra.enrico.base_android.extensions.viewLifecycleScope
import com.guerra.enrico.base_android.widget.SnackbarBuilder
import com.guerra.enrico.components.OverlayLoader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

open class BaseFragment : Fragment {
  private var snackbar: Snackbar? = null
  private lateinit var overlayLoader: OverlayLoader

  constructor() : super()
  constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    overlayLoader =
      OverlayLoader.make(requireActivity(), resources.getString(R.string.label_loading))
  }

  fun <T> observe(flow: Flow<T>, block: (T) -> Unit) {
    flow
      .onEach { block(it) }
      .launchIn(viewLifecycleScope)
  }

  fun <T> observeEvent(flow: Flow<Event<T>>, block: (T) -> Unit) {
    flow
      .onEach {
        val event = it.getContent() ?: return@onEach
        block(event)
      }
      .launchIn(viewLifecycleScope)
  }

  fun observeLoading(flow: Flow<Event<Boolean>>) {
    flow
      .onEach { event ->
        val show = event.getContent() ?: return@onEach
        if (show) {
          overlayLoader.show()
        } else {
          overlayLoader.hide()
        }
      }
      .launchIn(viewLifecycleScope)
  }

  fun showSnackbar(snackbarBuilder: SnackbarBuilder) {
    val snackbar = snackbarBuilder.build(requireActivity().findViewById(android.R.id.content))
    snackbar.show()
  }

  override fun onStop() {
    snackbar?.let {
      if (it.isShownOrQueued) {
        it.dismiss()
      }
    }
    snackbar = null
    super.onStop()
  }
}