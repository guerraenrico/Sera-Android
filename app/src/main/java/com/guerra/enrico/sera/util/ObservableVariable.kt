package com.guerra.enrico.sera.util

import io.reactivex.subjects.BehaviorSubject

/**
 * Created by enrico
 * on 10/06/2018.
 */
class ObservableVariable<T>(initalValue: T) {
    var value: T = initalValue
        set(value) {
            field = value
            observable.onNext(value)
        }
    var observable =  BehaviorSubject.createDefault(value)
}