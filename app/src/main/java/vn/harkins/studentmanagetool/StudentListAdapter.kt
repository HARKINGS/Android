package vn.harkins.studentmanagetool

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class StudentListAdapter(val items: ArrayList<ItemModel>): BaseAdapter() {
    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder: MyViewHolder
        val itemView: View

        if (convertView == null) {
            itemView = LayoutInflater
                .from(parent?.context)
                .inflate(R.layout.student_info, parent, false)
            viewHolder = MyViewHolder()
            viewHolder.hoten = itemView.findViewById(R.id.hotenTv)
            viewHolder.mssv = itemView.findViewById(R.id.mssvTv)
            viewHolder.delbtn = itemView.findViewById(R.id.delbtn)
            itemView.tag = viewHolder
        }
        else {
            itemView = convertView
            viewHolder = itemView.tag as MyViewHolder
        }

        viewHolder.hoten.text = items[position].hoten
        viewHolder.mssv.text = items[position].mssv
        viewHolder.delbtn.setOnClickListener {
            items.removeAt(position)
            notifyDataSetChanged()
        }

        return itemView
    }
}

class MyViewHolder {
    lateinit var hoten: TextView
    lateinit var mssv: TextView
    lateinit var delbtn: ImageView
}