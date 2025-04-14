package com.example.calculatorapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var tvInput: TextView
    private lateinit var tvOldInput: TextView

    private var currentInput: String = ""
    private var oldInput: String = ""
    private var operator: String? = null
    private var resultCalculated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
        tvOldInput = findViewById(R.id.tvOldInput)

        val numberButtons = listOf(
            R.id.btnZero to "0", R.id.btnOne to "1", R.id.btnTwo to "2",
            R.id.btnThree to "3", R.id.btnFour to "4", R.id.btnFive to "5",
            R.id.btnSix to "6", R.id.btnSeven to "7", R.id.btnEight to "8", R.id.btnNine to "9"
        )

        numberButtons.forEach { (id, value) ->
            findViewById<Button>(id).setOnClickListener { appendNumber(value) }
        }

        findViewById<Button>(R.id.btnDot).setOnClickListener { appendDot() }
        findViewById<Button>(R.id.btnPLus).setOnClickListener { selectOperator("+") }
        findViewById<Button>(R.id.btnMinus).setOnClickListener { selectOperator("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { selectOperator("*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { selectOperator("/") }
        findViewById<Button>(R.id.btnEqual).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.clear).setOnClickListener { clearEntry() }
        findViewById<Button>(R.id.allClear).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btnBackspace).setOnClickListener { backspace() }
        findViewById<Button>(R.id.btnSign).setOnClickListener { toggleSign() }
    }

    private fun appendNumber(number: String) {
        if (resultCalculated) {
            currentInput = ""
            resultCalculated = false
        }
        currentInput += number
        tvInput.text = currentInput
    }

    private fun appendDot() {
        if (!currentInput.contains(".")) {
            if (currentInput.isEmpty()) {
                currentInput = "0."
            } else {
                currentInput += "."
            }
            tvInput.text = currentInput
        }
    }

    private fun selectOperator(op: String) {
        if (currentInput.isNotEmpty()) {
            oldInput = currentInput
            operator = op
            currentInput = ""
            tvOldInput.text = "$oldInput $operator"
            tvInput.text = "0"
        }
    }

    private fun calculateResult() {
        if (oldInput.isNotEmpty() && currentInput.isNotEmpty() && operator != null) {
            val result = when (operator) {
                "+" -> oldInput.toDouble() + currentInput.toDouble()
                "-" -> oldInput.toDouble() - currentInput.toDouble()
                "*" -> oldInput.toDouble() * currentInput.toDouble()
                "/" -> {
                    if (currentInput.toDouble() == 0.0) {
                        tvInput.text = "Error"
                        return
                    } else {
                        oldInput.toDouble() / currentInput.toDouble()
                    }
                }
                else -> return
            }
            tvInput.text = result.toString().removeSuffix(".0")
            tvOldInput.text = ""
            currentInput = result.toString()
            oldInput = ""
            operator = null
            resultCalculated = true
        }
    }

    private fun clearEntry() {
        currentInput = ""
        tvInput.text = "0"
    }

    private fun clearAll() {
        currentInput = ""
        oldInput = ""
        operator = null
        tvInput.text = "0"
        tvOldInput.text = ""
    }

    private fun backspace() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
            tvInput.text = if (currentInput.isEmpty()) "0" else currentInput
        }
    }

    private fun toggleSign() {
        if (currentInput.isNotEmpty()) {
            if (currentInput.startsWith("-")) {
                currentInput = currentInput.substring(1)
            } else {
                currentInput = "-$currentInput"
            }
            tvInput.text = currentInput
        }
    }
}
