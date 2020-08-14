package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity() {
    enum class Operator {
        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE;

        companion object {
            fun get(from: CharSequence): Operator {
                return when (from) {
                    "+" -> Operator.PLUS
                    "-" -> Operator.MINUS
                    "*" -> Operator.MULTIPLY
                    "/" -> Operator.DIVIDE
                    else -> throw IllegalArgumentException("$from is unknown")
                }
            }
        }

        fun calculate(first: String, second: String): String {
            println(first)
            println(second)

            return when(this) {
                Operator.PLUS -> {
                    return if (first.isInt() && second.isInt()) {
                        (first.toInt() + second.toInt()).toString()
                    } else {
                        (first.toDouble() + second.toDouble()).toString()
                    }
                }
                Operator.MINUS -> {
                    return if (first.isInt() && second.isInt()) {
                        (first.toInt() - second.toInt()).toString()
                    } else {
                        (first.toDouble() - second.toDouble()).toString()
                    }
                }
                Operator.MULTIPLY -> {
                    return if (first.isInt() && second.isInt()) {
                        (first.toInt() * second.toInt()).toString()
                    } else {
                        (first.toDouble() * second.toDouble()).toString()
                    }
                }
                Operator.DIVIDE -> {
                    if (first.isInt() && second.isInt()) {
                        val firstVal = first.toInt()
                        val secondVal = second.toInt()
                        if (firstVal % secondVal == 0) {
                            return (firstVal / secondVal).toString()
                        }
                    }

                    return (first.toDouble() + second.toDouble()).toString()
                }
            }
        }
    }

    var input: String? = null
    var operator: Operator? = null
    var nextToOperator: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View) {
        if (view !is Button) return

        if (nextToOperator) {
            inputText.text = view.text
            nextToOperator = false
        } else {
            if (inputText.text == "0") {
                inputText.text = view.text
            } else {
                inputText.text = inputText.text.toString() + view.text.toString()
            }
        }
    }

    fun onPoint(view: View) {
        // いきなり小数点がきた場合（たぶん考慮不要）

        if (nextToOperator) {
            inputText.text = "0."
            nextToOperator = false
        } else {
            if (inputText.text.contains(".")) return
            inputText.text = inputText.text.toString() + "."
        }
    }

    fun onClear(view: View) {
        // nextToOperatorにまつわる処理

        inputText.text = "0"
        nextToOperator = false
    }

    fun onOperator(view: View) {
        if (view !is Button) return
        input = inputText.text.toString()
        operator = Operator.get(view.text)
        nextToOperator = true
    }

    fun onEqual(view: View) {
        val inputVal = input
        val operatorVal = operator
        if (inputVal == null || operatorVal == null) return

        inputText.text = operatorVal.calculate(inputVal, inputText.text.toString())
        input = null
        operator = null
        nextToOperator = true
    }
}

fun String.isInt(): Boolean {
    return !(this.contains("."))
}
