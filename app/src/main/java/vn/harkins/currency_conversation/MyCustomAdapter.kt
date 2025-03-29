package vn.harkins.currency_conversation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.ImageView

class MyCustomAdapter(private val itemList: Array<ItemModel>) : BaseAdapter() {
    override fun getCount(): Int = itemList.size
    override fun getItem(position: Int): Any = itemList[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context)
            .inflate(R.layout.custom_item_view, parent, false)

        // Tìm ImageView trong layout edit1
        val imgCurrency = view.findViewById<ImageView>(R.id.image_icon)
        // Gán ảnh usd.png vào ImageView
        imgCurrency.setImageResource(itemList[position].imageResource)

        // Nếu cần, bạn cũng có thể gán dữ liệu cho EditText hoặc các thành phần khác
        val etAmount = view.findViewById<EditText>(R.id.text_label)
        etAmount.setText(itemList[position].caption)
        // Ví dụ: etAmount.setText(itemList[position].amount.toString())

        return view
    }
}
