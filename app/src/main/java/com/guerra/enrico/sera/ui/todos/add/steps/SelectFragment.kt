package com.guerra.enrico.sera.ui.todos.add.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.ui.base.BaseFragment
import com.guerra.enrico.sera.ui.todos.add.TodoAddViewModel
import com.guerra.enrico.sera.util.activityViewModelProvider
import kotlinx.android.synthetic.main.fragment_todo_add_select.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 19/10/2018.
 */
class SelectFragment: BaseFragment() {
    lateinit var root: View

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: TodoAddViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_todo_add_select, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = activityViewModelProvider(viewModelFactory)

        buttonAddCategory.setOnClickListener {
            viewModel.goToNextStep(StepEnum.ADD_CATEGORY)
        }

        buttonAddTask.setOnClickListener {
            viewModel.goToNextStep(StepEnum.SELECT_CATEGORY)
        }
    }
}