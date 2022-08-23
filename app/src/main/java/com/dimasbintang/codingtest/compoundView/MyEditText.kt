package com.dimasbintang.codingtest.compoundView

import android.content.Context
import android.content.res.TypedArray
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.IntDef
import androidx.constraintlayout.widget.ConstraintLayout
import com.dimasbintang.codingtest.R
import kotlinx.android.synthetic.main.view_myedittext.view.*

class MyEditText  : ConstraintLayout {

    var title: String
        get() {
            return myedittext_tv_title.text.toString()
        }
        set(value) {
            myedittext_tv_title.text = value
        }

    var input: String
        get() {
            return myedittext_et_input.text.toString()
        }
        set(value) {
            myedittext_et_input.setText(value)
        }

    var inputHint: String
        get() {
            return myedittext_et_input.hint.toString()
        }
        set(value) {
            myedittext_et_input.hint = value
        }

    var error: String
        get() {
            return myedittext_tv_error.text.toString()
        }
        set(value) {
            myedittext_tv_error.text = value
            if (value.isEmpty()) {
                myedittext_tv_error.visibility = View.GONE
                myedittext_tv_title.visibility= View.VISIBLE
            } else {
                myedittext_tv_error.visibility = View.VISIBLE
                myedittext_tv_title.visibility= View.GONE
            }
        }

    @TextInputType
    var inputType: Long
        get() {
            when (myedittext_et_input.inputType) {
                (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) -> return PASSWORD
                (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL) -> return TEXT
                else -> {
                    return TEXT
                }
            }
        }
        set(value) {
            when (value) {
                PASSWORD -> myedittext_et_input.inputType = (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                TEXT -> myedittext_et_input.inputType = (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL)

                else -> {
                    myedittext_et_input.inputType = (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL)
                }
            }
        }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context,
                attrs: AttributeSet,
                defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {

        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_myedittext, this)

        // Default values
        error = ""
        inputType = TEXT

        if (attrs != null) {
            val typedArray: TypedArray = context
                .obtainStyledAttributes(attrs, R.styleable.MyEditText)

            if (typedArray.hasValue(R.styleable.MyEditText_title)) {
                title = typedArray
                    .getString(R.styleable.MyEditText_title).toString()
            }

            if (typedArray.hasValue(R.styleable.MyEditText_input)) {
                input = typedArray
                    .getString(R.styleable.MyEditText_input).toString()
            }

            if (typedArray.hasValue(R.styleable.MyEditText_inputHint)) {
                inputHint = typedArray
                    .getString(R.styleable.MyEditText_inputHint).toString()
            }

            if (typedArray.hasValue(R.styleable.MyEditText_error)) {
                error = typedArray
                    .getString(R.styleable.MyEditText_error).toString()
            }

            if (typedArray.hasValue(R.styleable.MyEditText_inputType)) {
                inputType = typedArray.getInt(R.styleable.MyEditText_inputType, TEXT.toInt()).toLong()
            }

            typedArray.recycle()
        }
    }

    fun setText(tempInput: String?) {
        myedittext_et_input.setText(tempInput)

    }

    fun setError() {
        myedittext_et_input.setError("This Field can't be empty")
    }

    companion object {

        @IntDef(TEXT.toInt(), PASSWORD.toInt())
        @Retention(AnnotationRetention.SOURCE)
        annotation class TextInputType

        const val TEXT = 0L
        const val PASSWORD = 1L
    }
}