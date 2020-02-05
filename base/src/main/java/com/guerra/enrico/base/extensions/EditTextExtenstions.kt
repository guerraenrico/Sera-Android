package com.guerra.enrico.base.extensions

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.AutoCompleteTextView
import android.widget.TextView

/**
 * Created by enrico
 * on 05/02/2020.
 */
fun AutoCompleteTextView.onSearch(block: (text: String) -> Unit) {
  setOnEditorActionListener(object : TextView.OnEditorActionListener {
    override fun onEditorAction(textView: TextView?, actionId: Int, event: KeyEvent?): Boolean {
      if (actionId == EditorInfo.IME_ACTION_SEARCH) {
        block(text.toString())
        return true
      }
      return false
    }
  })
}