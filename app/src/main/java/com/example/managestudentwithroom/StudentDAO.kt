package com.example.managestudentwithroom

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudentDAO {
    @Query("SELECT * FROM student")
    suspend fun getStudents(): List<Student>
    @Insert
    suspend fun insertStudent(student: Student)
    @Query("UPDATE student SET studentName = :studentName WHERE studentId = :studentId")
    suspend fun updateStudent(studentName: String, studentId: String)
    @Query("DELETE FROM student WHERE id = :id")
    suspend fun deleteStudent(id: Int)

}