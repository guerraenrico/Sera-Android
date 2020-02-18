package com.guerra.enrico.base.extensions

/**
 * Created by enrico
 * on 18/02/2020.
 */

typealias F1<T> = (T) -> T

typealias F2<T, A> = (T, A) -> T

typealias F3<T, A, B> = (T, A, B) -> T

infix fun <T> F1<T>.compose(f1: F1<T>): (T) -> T {
  return { t: T -> f1(this(t)) }
}

infix fun <T, A> F2<T, A>.compose(f1: F1<T>): (T, A) -> T {
  return { t: T, a: A -> f1(this(t, a)) }
}

infix fun <T, A, B> F3<T, A, B>.compose(f1: F1<T>): (T, A, B) -> T {
  return { t: T, a: A, b: B -> f1(this(t, a, b)) }
}