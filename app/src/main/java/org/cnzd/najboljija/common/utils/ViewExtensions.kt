package org.cnzd.najboljija.common.utils

import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.TextView


inline fun EditText.onTextChanged(crossinline onTextChangeHandler: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) = Unit
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(input: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChangeHandler(input?.toString() ?: "")
        }
    })
}


fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toInvisible() {
    this.visibility = View.INVISIBLE
}
