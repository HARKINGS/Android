package com.example.managestudentwithroom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentRecyclerAdapter(private val itemList: ArrayList<Student>,
                            private val db: StudentDatabase) :
    RecyclerView.Adapter<StudentRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studentName: TextView = itemView.findViewById(R.id.studentName)
        val studentId: TextView = itemView.findViewById(R.id.studentId)
        val btnDel: ImageButton = itemView.findViewById(R.id.btnDel)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_info, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentRecyclerAdapter.ViewHolder, position: Int) {
        val item = itemList[position]
        holder.studentName.text = item.studentName
        holder.studentId.text = item.studentId
        holder.btnDel.setOnClickListener {
            // Lấy vị trí của phần tử được nhấp vào
            val adapterPosition = holder.adapterPosition
            // Xóa phần tử khỏi danh sách
            // Thông báo cho RecyclerView rằng dữ liệu đã thay đổi
            if(adapterPosition != RecyclerView.NO_POSITION) {
                val studentToDelete = itemList[adapterPosition]

                CoroutineScope(Dispatchers.IO).launch {
                    db.getStudentDAO().deleteStudent(studentToDelete.id)

                    // Trở lại Main thread để cập nhật RecyclerView
                    withContext(Dispatchers.Main) {
                        itemList.removeAt(adapterPosition)
                        notifyItemRemoved(adapterPosition)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = itemList.size
}