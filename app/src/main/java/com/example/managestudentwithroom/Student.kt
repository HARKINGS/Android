package com.example.managestudentwithroom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val studentName: String? = null,
    val studentId: String? = null
)