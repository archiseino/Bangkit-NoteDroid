package com.example.storyapp.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.storyapp.R

class PasswordTextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : AppCompatEditText(context, attrs), View.OnTouchListener {

    private var startDrawable : Drawable
    private var clearButton: Drawable

    init {
        startDrawable = ContextCompat.getDrawable(context, R.drawable.custom_ic_password) as Drawable
        clearButton = ContextCompat.getDrawable(context, R.drawable.ic_clear) as Drawable
        setCompoundDrawablesWithIntrinsicBounds(startDrawable, null, null, null)
        setOnTouchListener(this)
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        if (text.toString().isNotEmpty()) showClearButton() else hideClearButton()
        handleErrorMessage(text.toString())
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val clearButtonStart: Float
            val clearButtonEnd: Float
            var isButtonClicked = false
            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                clearButtonStart = (clearButton.intrinsicWidth + paddingStart).toFloat()
                when {
                    event.x < clearButtonStart -> isButtonClicked = true
                }
            } else {
                clearButtonEnd = (width - clearButton.intrinsicWidth - paddingEnd).toFloat()
                when {
                    event.x > clearButtonEnd -> isButtonClicked = true
                }
            }
            if (isButtonClicked) {
                if (event.action == MotionEvent.ACTION_DOWN) {
                    showClearButton()
                    return true
                } else if (event.action == MotionEvent.ACTION_UP) {
                    text?.clear()
                    hideClearButton()
                    return true
                }
            }
        }
        return false
    }

    private fun handleErrorMessage(password: String) {
        error = if (password.isNotEmpty() && text.toString().length < 8) {
            context.getString(R.string.error_password)
        } else {
            null
        }
    }

    private fun showClearButton() {
        setCompoundDrawablesWithIntrinsicBounds(startDrawable, null, clearButton, null)
    }

    private fun hideClearButton() {
        setCompoundDrawablesWithIntrinsicBounds(startDrawable, null, null, null)
    }

}