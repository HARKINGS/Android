package com.example.managestudentwithroom

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var edtStudentName: EditText
    private lateinit var edtStudentId: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edtStudentName = findViewById(R.id.edtStudentName)
        edtStudentId = findViewById(R.id.edtStudentId)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)

        // val db = Room.databaseBuilder(applicationContext, StudentDatabase::class.java, "student_db").build()
        val db = Room.databaseBuilder(this,
            StudentDatabase::class.java,
            "student_db"
        ).build()

        val studentDAO = db.getStudentDAO()
        val itemList = ArrayList<Student>()
        val adapter = StudentRecyclerAdapter(itemList, db)

        // Thiết lập LayoutManager cho RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            val students = studentDAO.getStudents()

            launch(Dispatchers.Main) {
                itemList.clear()
                itemList.addAll(students)
                adapter.notifyDataSetChanged()
                Log.d("MainActivity", "Number of students: ${students.size}")
            }
        }

        btnAdd.setOnClickListener {
            val studentName = edtStudentName.text.toString()
            val studentId = edtStudentId.text.toString()

            if(studentName.isNotEmpty() && studentId.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    studentDAO.insertStudent(Student(studentName = studentName, studentId = studentId))
                }

                itemList.add(0, Student(studentName = studentName, studentId = studentId))

                adapter.notifyItemInserted(0)
                recyclerView.scrollToPosition(0)

                edtStudentName.text.clear()
                edtStudentId.text.clear()
            }
            else {
//                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                AlertDialog.Builder(this)
                    .setTitle("Thông báo")
                    .setMessage("Vui lòng nhập đầy đủ thông tin")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        // xem thông tin của toàn bộ sinh viên
        btnUpdate.setOnClickListener {
            val studentName = edtStudentName.text.toString()
            val studentId = edtStudentId.text.toString()

            if(studentName.isNotEmpty() && studentId.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    studentDAO.updateStudent(studentName, studentId)

                    launch(Dispatchers.Main) {
                        val updatedList = studentDAO.getStudents()

                        itemList.clear()
                        itemList.addAll(updatedList)
                        adapter.notifyDataSetChanged()

                        Toast.makeText(this@MainActivity, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else {
                AlertDialog.Builder(this)
                    .setTitle("Thông báo")
                    .setMessage("Vui lòng nhập đầy đủ thông tin")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
    }
}