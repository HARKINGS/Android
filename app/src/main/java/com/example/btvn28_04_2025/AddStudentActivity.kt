package com.example.btvn28_04_2025

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {
    private lateinit var editName: EditText
    private lateinit var editMssv: EditText
    private lateinit var editSdt: EditText
    private lateinit var editEmail: EditText
    private lateinit var btnAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        editName = findViewById(R.id.udName)
        editMssv = findViewById(R.id.editMssv)
        editSdt = findViewById(R.id.editSdt)
        editEmail = findViewById(R.id.editEmail)
        btnAdd = findViewById(R.id.btnAdd)

        btnAdd.setOnClickListener {
            val name = editName.text.toString()
            val mssv = editMssv.text.toString()
            val sdt = editSdt.text.toString()
            val email = editEmail.text.toString()

            val newStudent = StudentModel(name, mssv, sdt, email)

            val resultIntent = Intent() // Tạo một Intent mới
            resultIntent.putExtra("NEW_STUDENT", newStudent) // Đóng gói StudentModel vào Intent
            setResult(RESULT_OK, resultIntent) // Trả kết quả về Activity 1
            finish() // Kết thúc Activity 2
        }
    }
}