package com.dicoding.myviewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.dicoding.myviewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayResult()

        binding.btnCalculate.setOnClickListener {

            val width = binding.edtWidth.text.toString()
            val height = binding.edtHeight.text.toString()
            val length = binding.edtLength.text.toString()

            when {
                width.isEmpty() -> {
                    binding.edtWidth.error = "Masih Kosong"
                }
                height.isEmpty() -> {
                    binding.edtHeight.error = "Masih Kosong"
                }
                length.isEmpty() -> {
                    binding.edtLength.error = "Masih Kosong"
                }
                else -> {
                    viewModel.calculate(width, height, length)
                    displayResult()
                }
            }
        }

    }

    private fun displayResult() {
        binding.tvResult.text = viewModel.result.toString()
    }
}