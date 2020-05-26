package com.guerra.enrico.navigation.directions

import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContract
import com.guerra.enrico.navigation.di.ActivityTarget
import com.guerra.enrico.navigation.di.FragmentTarget

/**
 * Created by enrico
 * on 20/05/2020.
 */

interface Input<T: Parcelable> {
  val key: String
  val params: T
}

interface Output {
  val code: Int
  val dataKey: String
}

interface Result<I: Any, O> {
  fun getContract(clazz: Class<Any>): ActivityResultContract<I, O?>
}


interface FragmentDirection {
  val target: FragmentTarget
}

interface ActivityDirection {
  val target: ActivityTarget
}