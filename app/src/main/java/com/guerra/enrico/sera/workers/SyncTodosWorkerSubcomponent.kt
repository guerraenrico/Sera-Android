package com.guerra.enrico.sera.workers

import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by enrico
 * on 26/12/2018.
 */
@Subcomponent
interface SyncTodosWorkerSubcomponent: AndroidInjector<SyncTodosWorker> {
    @Subcomponent.Builder
    abstract class Builder: AndroidInjector.Builder<SyncTodosWorker>()
}