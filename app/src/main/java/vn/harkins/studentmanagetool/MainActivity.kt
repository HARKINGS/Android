package vn.harkins.studentmanagetool

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class ItemModel (
    val hoten: String,
    val mssv: String,
    val delbtn: ImageView
)

class MainActivity : AppCompatActivity() {
    lateinit var hoten: TextView
    lateinit var mssv: TextView
    lateinit var addbtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val itemList = arrayListOf<ItemModel>()

        val adapter = StudentListAdapter(itemList)
        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter

//        val adapter = StudentRecyclerAdapter(itemList)
//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = adapter

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
                // Thêm dữ liệu vào danh sách
                itemList.add(0,
                    ItemModel(
                        "Họ tên: ${hoten.text}",
                        "MSSV: ${mssv.text}",
                        ImageView(this))
                )

                // Cập nhật ListView
                adapter.notifyDataSetChanged()

                // Xóa nội dung nhập
                hoten.text = ""
                mssv.text = ""
            }
        }
    }
}