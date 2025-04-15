package vn.harkins.studentmanagetool

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentRecyclerAdapter(private val itemList: ArrayList<ItemModel2>) :
    RecyclerView.Adapter<StudentRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hoten: TextView = itemView.findViewById(R.id.hotenTv)
        val mssv: TextView = itemView.findViewById(R.id.mssvTv)
        val delBtn: ImageView = itemView.findViewById(R.id.delbtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_info, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.hoten.text = item.hoten
        holder.mssv.text = item.mssv

        holder.delBtn.setOnClickListener {
            val adapterPosition = holder.adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                itemList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
                // Không cần thêm notifyItemRangeChanged ở đây vì không cần thiết
            }
        }
    }

    override fun getItemCount(): Int = itemList.size
}