package com.guerra.enrico.sera.ui.todos.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.ui.base.BaseFragment
import com.guerra.enrico.sera.ui.todos.TodosViewModel
import kotlinx.android.synthetic.main.fragment_todos_filters.*
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * Created by enrico
 * on 18/08/2018.
 */
class TodosFilterFragment : BaseFragment() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private val viewModel: TodosViewModel by viewModels { viewModelFactory }
  private val behavior by lazy {
    WeakReference(BottomSheetBehavior.from(filters_bottom_sheet))
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_todos_filters, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    buttonCollapse.setOnClickListener {
      behavior.get()?.apply {
        state = BottomSheetBehavior.STATE_HIDDEN
      }
    }
  }
}