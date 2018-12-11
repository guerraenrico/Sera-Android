package com.guerra.enrico.sera.ui.charts

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.data.exceptions.OperationException
import com.guerra.enrico.sera.navigation.NavigationModel
import com.guerra.enrico.sera.ui.base.BaseActivity
import com.guerra.enrico.sera.util.viewModelProvider
import com.guerra.enrico.sera.widget.MessageLayout
import kotlinx.android.synthetic.main.activity_charts.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 27/05/2018.
 */
class ChartsActivity: BaseActivity(){
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ChartsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charts)
        viewModel = viewModelProvider(viewModelFactory)

        toolbarTitle?.text = resources.getString(R.string.title_charts)
        initView()
    }

    override fun initView() {
        messageLayput.setMessage(OperationException.UnknownError().getBaseMessage()) { code ->

        }

        messageLayput.show()
    }

    override fun getSelfNavDrawerItem(): NavigationModel.NavigationItemEnum {
        return NavigationModel.NavigationItemEnum.CHARTS
    }

}