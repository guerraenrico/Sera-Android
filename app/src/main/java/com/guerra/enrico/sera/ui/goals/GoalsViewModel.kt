package com.guerra.enrico.sera.ui.goals

import com.guerra.enrico.sera.ui.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by enrico
 * on 18/08/2018.
 */
class GoalsViewModel @Inject constructor(
        compositeDisposable: CompositeDisposable
) : BaseViewModel(compositeDisposable)