package com.programmerzamannow.barvolume

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    private lateinit var editWidth: EditText
    private lateinit var editHeight: EditText
    private lateinit var editLength: EditText
    private lateinit var btnCalculate: Button
    private lateinit var tvResult: TextView

    companion object {
        private const val STATE_RESULT = "state_result"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editWidth = findViewById(R.id.edit_width)
        editHeight = findViewById(R.id.edit_height)
        editLength = findViewById(R.id.edit_length)
        btnCalculate = findViewById(R.id.btn_calculate)
        tvResult = findViewById(R.id.tv_result)

        btnCalculate.setOnClickListener {
            if (it.id == R.id.btn_calculate) {
                val inputWidth = editWidth.text.toString().trim()
                val inputHeight = editHeight.text.toString().trim()
                val inputLength = editLength.text.toString().trim()

                var isEmptyFields = false

                if (inputWidth.isEmpty()) {
                    isEmptyFields = true
                    editWidth.error = "Field ini tidak boleh kosong"
                }
                if (inputHeight.isEmpty()) {
                    isEmptyFields = true
                    editHeight.error = "Field ini tidak boleh kosong"
                }
                if (inputLength.isEmpty()) {
                    isEmptyFields = true
                    editLength.error = "Field ini tidak boleh kosong"
                }
                if (!isEmptyFields) {
                    val volume =
                        inputWidth.toDouble() * inputHeight.toDouble() * inputLength.toDouble()
                    tvResult.text = volume.toString()
                }

            }
        }

        if (savedInstanceState != null) {
            val result = savedInstanceState.getString(STATE_RESULT)
            tvResult.text = result
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT, tvResult.text.toString())
    }

}