package com.bugull.android.extension

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView

// View的扩展

/* Edit.addTextChangedListener start */
fun EditText.textWatcher(block: TextWatcherListenerBuilder.() -> Unit) {
    val builder = TextWatcherListenerBuilder().also(block)
    addTextChangedListener(object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            builder.ingAction?.invoke(s)
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            builder.beforeAction?.invoke(s)
        }

        override fun afterTextChanged(s: Editable) {
            builder.afterAction?.invoke(s)
        }
    })
}

class TextWatcherListenerBuilder {
    internal var beforeAction: ((CharSequence) -> Unit)? = null
    internal var afterAction: ((CharSequence) -> Unit)? = null
    internal var ingAction: ((CharSequence) -> Unit)? = null

    fun before(action: (CharSequence) -> Unit) {
        beforeAction = action
    }

    fun after(action: (CharSequence) -> Unit) {
        afterAction = action
    }

    fun ing(action: (CharSequence) -> Unit) {
        ingAction = action
    }
}

/* Edit.addTextChangedListener end */



/* Edit.setFilters start */

fun EditText.filterText(vararg filterArray: InputFilter) {
    filters = filterArray
}

fun EditText.filterPassword() {
    filterText(InputFilter.LengthFilter(16))
}

/* Edit.setFilters end */

fun TextView.toStr() = this.text.toString()