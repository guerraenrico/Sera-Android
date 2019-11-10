package com.guerra.enrico.domain

/**
 * Created by enrico
 * on 10/11/2019.
 */
abstract class Interactor<in P, out R> {
  protected abstract fun doWork(params: P): R
  fun execute(params: P) = doWork(params)
}
