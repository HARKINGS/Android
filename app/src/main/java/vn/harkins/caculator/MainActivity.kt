package vn.harkins.caculator

import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var textAns: TextView
    private lateinit var preAns: TextView
    private lateinit var btnNumbers: Array<Button>
    private lateinit var btnClear: Button
    private lateinit var btnClearEnd: Button
    private lateinit var btnPlus: Button
    private lateinit var btnSub: Button
    private lateinit var btnMul: Button
    private lateinit var btnDiv: Button
    private lateinit var btnConvert: Button
    private lateinit var btnEqual: Button
    private lateinit var btnBackSpace: Button

    private var num1: Double = 0.0
    private var num2: Double = 0.0
    private var ok: BooleanArray = BooleanArray(5) {false}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        preAns = findViewById(R.id.preAns)
        textAns = findViewById(R.id.textAns)
        btnConvert = findViewById(R.id.btnPDS)
        btnEqual = findViewById(R.id.btnEqual)
        btnPlus = findViewById(R.id.btnPlus)
        btnSub = findViewById(R.id.btnSub)
        btnMul = findViewById(R.id.btnMul)
        btnDiv = findViewById(R.id.btnDiv)
        btnClear = findViewById(R.id.btnC)
        btnClearEnd = findViewById(R.id.btnCE)
        btnBackSpace = findViewById(R.id.btnBS)

        btnNumbers = arrayOf(
            findViewById(R.id.btn0),
            findViewById(R.id.btn1),
            findViewById(R.id.btn2),
            findViewById(R.id.btn3),
            findViewById(R.id.btn4),
            findViewById(R.id.btn5),
            findViewById(R.id.btn6),
            findViewById(R.id.btn7),
            findViewById(R.id.btn8),
            findViewById(R.id.btn9),
            findViewById(R.id.btnDot)
        )

        btnNumbers.forEach { button ->
            button.setOnClickListener {
                val currentText = textAns.text.toString()
                val newText = if (currentText == "0") {
                                button.text.toString() // Nếu đang là "0", thay thế bằng số mới
                            } else {
                                currentText + button.text.toString() // Nếu không, nối tiếp chuỗi
                            }

                textAns.text = newText
            }
        }

        btnClear.setOnClickListener {
            textAns.text = "0"
            preAns.text = ""
            ok.indices.forEach { i -> ok[i] = false }
            num1 = 0.0
            num2 = 0.0
        }

        btnClearEnd.setOnClickListener {
            val currentText = textAns.text.toString()
            if (currentText.isNotEmpty()) {
                textAns.text = currentText.substring(0, currentText.length - 1)
            }
        }

        btnBackSpace.setOnClickListener {
            val currentText = textAns.text.toString()
            if (currentText.isNotEmpty()) {
                textAns.text = currentText.substring(0, currentText.length - 1)
            }
        }

        btnConvert.setOnClickListener {
            textAns.setText("-" + textAns.text.toString())
        }

        btnPlus.setOnClickListener {
            typeActivation(1, "+")
        }

        btnSub.setOnClickListener {
            typeActivation(2, "-")
        }

        btnMul.setOnClickListener {
            typeActivation(3, "*")
        }

        btnDiv.setOnClickListener {
            typeActivation(4, "/")
        }

        btnEqual.setOnClickListener {
            calculator()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun isValidNumber(input: String): Boolean {
        return try {
            input.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    private fun typeActivation(type: Int, sign: String = "") {
        num1 = textAns.text.toString().toDouble()
        preAns.setText(num1.toString() + sign)
        textAns.text = ""
        ok[type] = true
    }

    private fun calculator() {
        num2 = textAns.text.toString().toDouble()
        preAns.setText(preAns.text.toString() + num2.toString() + "=")
        var cnt: Int = 0
        ok.indices.forEach { i ->
            if (ok[i]) {
                cnt++
            }
        }

        if (cnt != 1) {
            textAns.text = ""
        }

        if (ok[1]) {
            textAns.text = (num1 + num2).toString()
        }

        if (ok[2]) {
            textAns.text = (num1 - num2).toString()
        }

        if (ok[3]) {
            textAns.text = (num1 * num2).toString()
        }

        if (ok[4]) {
            if (num2 == 0.0) {
                textAns.text = "Error"
                return
            }
            textAns.text = (num1 / num2).toString()
        }

        ok.indices.forEach { i -> ok[i] = false }

        num1 = 0.0
        num2 = 0.0
    }
}