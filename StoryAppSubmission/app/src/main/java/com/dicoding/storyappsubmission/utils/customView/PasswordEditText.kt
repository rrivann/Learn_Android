package com.dicoding.storyappsubmission.utils.customView

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.dicoding.storyappsubmission.R

class PasswordEditText : AppCompatEditText {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var isPasswordVisible = false

    private val showPasswordDrawable =
        ContextCompat.getDrawable(context, R.drawable.ic_visibility_black_24) as Drawable
    private val hidePasswordDrawable =
        ContextCompat.getDrawable(context, R.drawable.ic_visibility_off_black_24) as Drawable

    init {
        setup()
    }

    private fun setup() {
        setDrawable(showPasswordDrawable)
        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        compoundDrawablePadding = 16
        setHint(R.string.password)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(s: Editable?) {
                error =
                    if (s != null && s.toString().length < 8) context.getString(R.string.et_password_error_message) else null
            }
        })
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (compoundDrawables[0] != null && event.action == MotionEvent.ACTION_UP) {
            val leftDrawableWidth = compoundDrawables[0].bounds.width()

            if (event.rawX <= leftDrawableWidth + paddingLeft) {
                togglePasswordVisibility()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
        if (isPasswordVisible) {
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            setDrawable(hidePasswordDrawable)
        } else {
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            setDrawable(showPasswordDrawable)
        }
    }

    private fun setDrawable(
        start: Drawable? = null,
        top: Drawable? = null,
        end: Drawable? = null,
        bottom: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom)
    }

}
