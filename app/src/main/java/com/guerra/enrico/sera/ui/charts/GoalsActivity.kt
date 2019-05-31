package com.guerra.enrico.sera.ui.charts

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.data.exceptions.OperationException
import com.guerra.enrico.sera.navigation.NavigationModel
import com.guerra.enrico.sera.ui.base.BaseActivity
import com.guerra.enrico.sera.util.viewModelProvider
import kotlinx.android.synthetic.main.activity_goals.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 27/05/2018.
 */
class GoalsActivity: BaseActivity(){
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: GoalsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals)
        viewModel = viewModelProvider(viewModelFactory)

        toolbarTitle?.text = resources.getString(R.string.title_goals)
        initView()
    }

    override fun initView() {
        messageLayput.setMessage(OperationException.UnknownError().getBaseMessage()) { code ->

        }

        messageLayput.show()
    }

    override fun getSelfNavDrawerItem(): NavigationModel.NavigationItemEnum {
        return NavigationModel.NavigationItemEnum.GOALS
    }

}