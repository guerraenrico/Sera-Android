package com.guerra.enrico.base.extensions

private typealias F1<T> = (T) -> T

private typealias F2<T, A> = (T, A) -> T

private typealias F3<T, A, B> = (T, A, B) -> T

infix fun <T> F1<T>.compose(f1: F1<T>): (T) -> T {
  return { t: T -> f1(this(t)) }
}

infix fun <T, A> F2<T, A>.compose(f1: F1<T>): (T, A) -> T {
  return { t: T, a: A -> f1(this(t, a)) }
}

infix fun <T, A, B> F2<T, A>.compose(f2: F2<T, B>): (T, A, B) -> T {
  return { t: T, a: A, b: B -> f2(this(t, a), b) }
}

infix fun <T, A, B> F3<T, A, B>.compose(f1: F1<T>): (T, A, B) -> T {
  return { t: T, a: A, b: B -> f1(this(t, a, b)) }
}