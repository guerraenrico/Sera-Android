package com.guerra.enrico.sera.ui.results

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.guerra.enrico.base.util.viewModelProvider
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.exceptions.MessageExceptionManager
import com.guerra.enrico.sera.navigation.NavigationModel
import com.guerra.enrico.sera.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_goals.*
import kotlinx.android.synthetic.main.activity_results.*
import kotlinx.android.synthetic.main.activity_results.messageLayout
import kotlinx.android.synthetic.main.toolbar.*
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by enrico
 * on 30/07/2019.
 */
class ResultsActivity : BaseActivity() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private lateinit var viewModel: ResultsViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_results)
    viewModel = viewModelProvider(viewModelFactory)

    toolbarTitle?.text = resources.getString(R.string.title_results)
    initView()
  }

  override fun initView() {
    messageLayout.setMessage(MessageExceptionManager(Exception()).getBaseMessage()) {}
    messageLayout.show()
  }

  override fun getSelfNavDrawerItem(): NavigationModel.NavigationItemEnum {
    return NavigationModel.NavigationItemEnum.RESULTS
  }
}