package com.guerra.enrico.todos.add.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.todos.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by enrico
 * on 19/10/2018.
 */
@AndroidEntryPoint
internal class DoneFragment : BaseFragment() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_todo_add_done, container, false)
  }
}