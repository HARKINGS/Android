package com.example.btvn28_04_2025

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ListView
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    private lateinit var mainMenu: Toolbar
    private lateinit var studentsListView: ListView
    private lateinit var adapter: StudentsAdapter
    private val studentsList: ArrayList<StudentModel> = ArrayList()

    private lateinit var btnSetting: ImageView

    // Khởi tạo ActivityResultLauncher
    private val addStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val newStudent = data?.getParcelableExtra<StudentModel>("NEW_STUDENT")
            if (newStudent != null) {
                studentsList.add(newStudent)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Tìm Toolbar và thiết lập ActionBar
        mainMenu = findViewById(R.id.mainMenu)
        setSupportActionBar(mainMenu)

        // Khởi tạo ListView và Adapter
        studentsListView = findViewById(R.id.studentsList)
        adapter = StudentsAdapter(studentsList)
        studentsListView.adapter = adapter

        // Thêm dữ liệu ban đầu
        studentsList.add(StudentModel("Nguyen Van A", "123456", "0123456789", "a@gmail.com"))
        studentsList.add(StudentModel("Tran Thi B", "654321", "0987654321", "b@gmail.com"))
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                val intent = Intent(this, AddStudentActivity::class.java)
                addStudentLauncher.launch(intent) // Khởi chạy AddStudentActivity với ActivityResultLauncher
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}