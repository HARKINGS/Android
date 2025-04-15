package vn.harkins.studentmanagetool

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class ItemModel2 (
    val hoten: String,
    val mssv: String
)

class RecyclerActivity: AppCompatActivity() {
    lateinit var hoten: EditText  // Đổi từ TextView sang EditText
    lateinit var mssv: EditText   // Đổi từ TextView sang EditText
    lateinit var addbtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        val itemList = arrayListOf<ItemModel2>()

        val adapter = StudentRecyclerAdapter(itemList)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Thiết lập LayoutManager cho RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        hoten = findViewById(R.id.hoten)
        mssv = findViewById(R.id.mssv)
        addbtn = findViewById(R.id.addbtn)

        addbtn.setOnClickListener {
            val strHoten: String = hoten.text.toString()
            val strMssv: String = mssv.text.toString()

            if(strHoten.isEmpty() || strMssv.isEmpty()) {
                AlertDialog.Builder(this)
                    .setTitle("Lỗi")
                    .setMessage("Vui lòng nhập đầy đủ thông tin")
                    .setPositiveButton("OK", null)
                    .show()
            }
            else {
                // Thêm dữ liệu vào đầu danh sách
                itemList.add(0,
                    ItemModel2(
                        "Họ tên: $strHoten",
                        "MSSV: $strMssv"
                    )
                )

                // Thông báo cập nhật cho adapter
                adapter.notifyItemInserted(0)

                // Cuộn lên đầu danh sách để hiển thị item mới
                recyclerView.scrollToPosition(0)

                // Xóa nội dung nhập
                hoten.setText("")
                mssv.setText("")
            }
        }
    }
}