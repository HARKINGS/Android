package vn.harkins.studentmanagetool

import android.database.sqlite.SQLiteDatabase
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
    val mssv: String
)

class MainActivity : AppCompatActivity() {
    private lateinit var hoten: TextView
    private lateinit var mssv: TextView
    private lateinit var addbtn: Button
    private lateinit var querybtn: Button
    private lateinit var db: SQLiteDatabase
    private val itemList = arrayListOf<ItemModel>()
    private lateinit var adapter: StudentListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

//        val adapter = StudentRecyclerAdapter(itemList)
//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = adapter

        hoten = findViewById(R.id.hoten)
        mssv = findViewById(R.id.mssv)
        addbtn = findViewById(R.id.addbtn)
        querybtn = findViewById(R.id.querybtn)

        db = openOrCreateDatabase("student.db", MODE_PRIVATE, null)

//        tao table de chua co so du lieu
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS student (id INTEGER PRIMARY KEY AUTOINCREMENT, hoten TEXT, mssv TEXT)")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        adapter = StudentListAdapter(this, itemList, db)
        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter

        loadStudentFromDb()

        addbtn.setOnClickListener {
            val strHoten: String = hoten.text.toString()
            val strMssv: String = mssv.text.toString()

            if (strHoten.isEmpty() || strMssv.isEmpty()) {
                AlertDialog.Builder(this)
                    .setTitle("Lỗi")
                    .setMessage("Vui lòng nhập đầy đủ thông tin")
                    .setPositiveButton("OK", null)
                    .show()
            } else {

                // Thêm dữ liệu vào danh sách (KHÔNG format chuỗi ở đây)
                itemList.add(
                    0,
                    ItemModel(
                        strHoten,
                        strMssv
                    )
                )

                // Thêm vào database
                db.execSQL("INSERT INTO student (hoten, mssv) VALUES (?, ?)", arrayOf(strHoten, strMssv))
//                db.execSQL("SELECT * FROM student")

                // Cập nhật ListView
                adapter.notifyDataSetChanged()

                // Xóa nội dung nhập
                hoten.text = ""
                mssv.text = ""

            }
        }

        querybtn.setOnClickListener {
            loadStudentFromDb()
        }
    }

    fun loadStudentFromDb() {
        itemList.clear()
        val cursor = db.rawQuery("SELECT * FROM student", null)
        if (cursor.moveToFirst()) {
            do {
                val hotenFromDb = cursor.getString(cursor.getColumnIndexOrThrow("hoten"))
                val mssvFromDb = cursor.getString(cursor.getColumnIndexOrThrow("mssv"))
                itemList.add(ItemModel(hotenFromDb, mssvFromDb))
            } while (cursor.moveToNext())
        }
        cursor.close()
        adapter.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        db.close()
    }
}