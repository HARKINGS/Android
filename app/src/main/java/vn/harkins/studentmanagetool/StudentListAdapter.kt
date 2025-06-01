package vn.harkins.studentmanagetool

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.openOrCreateDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class StudentListAdapter(
    private val context: Context,
    private val items: ArrayList<ItemModel>,
    private val db: SQLiteDatabase): BaseAdapter() {

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

        viewHolder.hoten.text = "Họ tên: ${items[position].hoten}"
        viewHolder.mssv.text = "MSSV: ${items[position].mssv}"

        viewHolder.delbtn.setOnClickListener {
            db.execSQL("DELETE FROM student WHERE mssv = ?", arrayOf(items[position].mssv))
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