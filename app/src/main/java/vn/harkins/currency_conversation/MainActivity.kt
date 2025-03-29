package vn.harkins.currency_conversation

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener

data class ItemModel (
    val caption: String,
    val imageResource: Int
)

class MainActivity : AppCompatActivity() {
    lateinit var img: Array<ImageView>
    lateinit var txtIPO: Array<TextView>

    lateinit var btnNumbers: Array<Button>
    lateinit var btnDot: Button
    lateinit var btnCE: Button
    lateinit var btnBS: Button

    lateinit var txtUpdated: TextView

    lateinit var arrayAdapterStr: ArrayAdapter<String>
    lateinit var items: Array<String>
    lateinit var currencyItems: Array<ItemModel>

    var currencyValue: Array<Double> = arrayOf(1.0, 0.0059, 0.000039, 0.000030, 0.000036)
    var type: Array<Int> = arrayOf(1, 1)
    var currentInputId = 0 // 0 = txtIPO[0], 1 = txtIPO[1]

    fun setUp() {
        btnDot = findViewById(R.id.btnDot)
        btnCE = findViewById(R.id.btnCE)
        btnBS = findViewById(R.id.btnBS)
        txtUpdated = findViewById(R.id.txtUpdated)

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
        )

        // Khởi tạo mảng với 2 phần tử
        txtIPO = arrayOf(
            findViewById(R.id.txtInput),
            findViewById(R.id.txtOutput)
        )

        img = arrayOf(
            findViewById(R.id.img0),
            findViewById(R.id.img1)
        )

        if(currentInputId == 0 || txtIPO[0].hasOnClickListeners()) {
            currentInputId = 0
            println("currentInputId: $currentInputId")
            onClickNumbers(txtIPO[0])
            onClickDot(txtIPO[0])
            onClickBS(txtIPO[0])
            onClickCE(txtIPO[0])
            convertCurrency(0, 1)
        }

        txtIPO[0].setOnClickListener{
            currentInputId = 0
            println("currentInputId: $currentInputId")
            onClickNumbers(txtIPO[0])
            onClickDot(txtIPO[0])
            onClickBS(txtIPO[0])
            onClickCE(txtIPO[0])
            convertCurrency(0, 1)
        }

        txtIPO[1].setOnClickListener {
            currentInputId = 1
            println("currentInputId: $currentInputId")
            onClickNumbers(txtIPO[1])
            onClickDot(txtIPO[1])
            onClickBS(txtIPO[1])
            onClickCE(txtIPO[1])
            convertCurrency(1, 0)
        }
    }

    fun setUpSpinner(imageResource: Int,
                     id: Int = 0) {
        findViewById<Spinner>(imageResource).run {
            adapter = arrayAdapterStr
            onItemSelectedListener = object : OnItemSelectedListener,
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    img[id].setImageResource(currencyItems[p2].imageResource)
                    type[id] = p2
                    convertCurrency(currentInputId, 1 - currentInputId)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    TODO("Not yet implemented")
                }
            }
        }
    }

    fun convertCurrency(type1: Int, type2: Int) {
        val rate1 = currencyValue[type[type1]]
        val rate2 = currencyValue[type[type2]]

        println("loai $type1, gia tri $rate1")
        println("loai $type2, gia tri $rate2")

        val value = txtIPO[type1].text.toString().toDouble()
        println("gia tri $value")

        val convertValue = (1.0 * value * (rate2 / rate1))
        println(convertValue)

        val result = if (convertValue == convertValue.toInt().toDouble()) {
            convertValue.toInt().toString() // Nếu là số nguyên, chuyển thành Int rồi String
        } else {
            convertValue.toString() // Nếu là số thực, giữ nguyên
        }

        txtIPO[type2].setText(result)
    }

    fun onClickNumbers(txtInput: TextView) {
        btnNumbers.forEach { button ->
            button.setOnClickListener {
                val currentText = txtInput.text.toString()
                println(currentText)
                val newText = if (currentText == "0" || currentText =="0.0") {
                    button.text.toString() // Nếu đang là "0", thay thế bằng số mới
                } else {
                    currentText + button.text.toString() // Nếu không, nối tiếp chuỗi
                }

                if(newText != "0.0") txtInput.setText(newText)
                else txtInput.setText("0")
                convertCurrency(currentInputId, 1 - currentInputId)
            }
        }
    }

    fun onClickDot(txtInput: TextView) {
        btnDot.setOnClickListener {
            val currentText = txtInput.text.toString()

            val newText = if (currentText.contains('.')) {
                currentText
            } else {
                currentText + '.'
            }

            if(newText != "0.0") txtInput.setText(newText)
            else txtInput.setText("0")
            convertCurrency(currentInputId, 1 - currentInputId)
        }
    }

    fun onClickBS(txtInput: TextView) {
        btnBS.setOnClickListener {

            val currentText = txtInput.text.toString()

            if (currentText.length > 1) {
                val newText = currentText.substring(0, currentText.length - 1)
                if(newText != "0.0") txtInput.setText(newText)
                else txtInput.setText("0")
            } else {
                txtInput.setText("0")
            }

            convertCurrency(currentInputId, 1 - currentInputId)
        }
    }

    fun onClickCE(txtInput: TextView) {
        btnCE.setOnClickListener {
            txtInput.setText("0")
            convertCurrency(currentInputId, 1 - currentInputId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        setUp()

        /// currencyItems phục vụ việc chuyển đổi ảnh
        currencyItems = arrayOf(
            ItemModel("VND", R.drawable.vnd),
            ItemModel("JPY", R.drawable.jpy),
            ItemModel("USD", R.drawable.usd),
            ItemModel("GBP", R.drawable.gbp),
            ItemModel("EUR", R.drawable.eur),
        )

        /// items phục vụ cho chuển đổi đơn vị tiền tệ
        items = arrayOf(
            "Vietnam - VND",
            "Japan - Yen",
            "United States - Dollar",
            "United Kingdom - Pound",
            "Germany - Euro"
        )

        arrayAdapterStr = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            items
        )

        setUpSpinner(R.id.spinnerInput, 0)
        setUpSpinner(R.id.spinnerOutput, 1)
    }
}