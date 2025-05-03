package com.example.btvn28_04_2025

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast

class StudentsAdapter(private val items: ArrayList<StudentModel>) : BaseAdapter() {
    override fun getCount(): Int = items.size
    override fun getItem(position: Int): Any = items[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView: View
        val viewHolder: ViewHolder
        val student = items[position] // Lấy StudentModel từ danh sách

        if (convertView == null) {
            val context = parent?.context!!
            val inflater = LayoutInflater.from(parent?.context)
            rowView = inflater.inflate(R.layout.item_student, parent, false)
            val textName: TextView = rowView.findViewById(R.id.textName) // Sửa ở đây
            val textMssv: TextView = rowView.findViewById(R.id.textMssv) // Sửa ở đây
            val btnSetting: ImageView = rowView.findViewById(R.id.btnSetting) // Sửa ở đây

            btnSetting.setOnClickListener {
                setUpContext(context, it, position, student, items)
            }

            viewHolder = ViewHolder(textName, textMssv, btnSetting) // Chỉ truyền 2 TextView vào ViewHolder
            rowView.tag = viewHolder
        } else {
            rowView = convertView
            viewHolder = rowView.tag as ViewHolder
        }

        // Cập nhật dữ liệu cho TextView
        viewHolder.name.text = student.name
        viewHolder.mssv.text = student.mssv
        viewHolder.btnSetting.setImageResource(R.drawable.settings_action)

        return rowView
    }

    fun setUpContext(context: Context, it: View, position: Int, student: StudentModel, students: ArrayList<StudentModel> ) {
        val popupMenu = PopupMenu(context, it)
        popupMenu.inflate(R.menu.popup_menu)
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.menu_update -> {
                        Toast.makeText(context, "Update: ${student.name}", Toast.LENGTH_SHORT).show()
                        // Gọi Intent cập nhật hoặc mở dialog
                        showUpdateDialog(context, student)
                    }
                    R.id.menu_delete -> {
                        Toast.makeText(context, "Delete: ${student.name}", Toast.LENGTH_SHORT).show()
                        students.removeAt(position)
                        notifyDataSetChanged()
                    }
                    R.id.menu_call -> {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${student.sdt}"))
                        context.startActivity(intent)
                    }
                    R.id.menu_email -> {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:${student.email}")
                            putExtra(Intent.EXTRA_SUBJECT, "Gửi email đến ${student.name}")
                        }
                        context.startActivity(intent)
                    }
                }
                return true
            }
        })
        popupMenu.show()
    }

    fun showUpdateDialog(context: Context, student: StudentModel) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.activity_update_student)

        val udName: EditText = dialog.findViewById(R.id.udName)
        val udMssv: EditText = dialog.findViewById(R.id.udMssv)
        val udSDT: EditText = dialog.findViewById(R.id.udSDT)
        val udEmail: EditText = dialog.findViewById(R.id.udEmail)
        val btnUpdate: Button = dialog.findViewById(R.id.btnUpdate)

        udName.setText(student.name)
        udMssv.setText(student.mssv)
        udSDT.setText(student.sdt)
        udEmail.setText(student.email)

        btnUpdate.setOnClickListener {
            student.name = udName.text.toString()
            student.mssv = udMssv.text.toString()
            student.sdt = udSDT.text.toString()
            student.email = udEmail.text.toString()

            notifyDataSetChanged()
            dialog.dismiss()
        }

        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.show()
    }

    class ViewHolder(
        var name: TextView,
        var mssv: TextView,
        var btnSetting: ImageView
    )
}