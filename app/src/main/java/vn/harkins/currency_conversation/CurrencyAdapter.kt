package vn.harkins.currency_conversation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import java.util.Locale

class CurrencyAdapter(
    context: Context,
    private val resource: Int,
    private val items: Array<ItemModel>
) : ArrayAdapter<ItemModel>(context, resource, items), Filterable {

    private var filteredItems: Array<ItemModel> = items

    override fun getCount(): Int {
        return filteredItems.size
    }

    override fun getItem(position: Int): ItemModel? {
        return filteredItems[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val imgCurrency = view.findViewById<ImageView>(R.id.image_icon)
        val txtCurrency = view.findViewById<TextView>(R.id.text_label)

        val item = getItem(position)
        item?.let {
            imgCurrency.setImageResource(it.imageResource)
            txtCurrency.text = it.caption
        }

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase(Locale.getDefault()) ?: ""
                val results = FilterResults()
                results.values = if (query.isEmpty()) {
                    items
                } else {
                    items.filter {
                        it.caption.lowercase(Locale.getDefault()).contains(query)
                    }
                }
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredItems = results?.values as Array<ItemModel>
                notifyDataSetChanged()
            }
        }
    }
}
