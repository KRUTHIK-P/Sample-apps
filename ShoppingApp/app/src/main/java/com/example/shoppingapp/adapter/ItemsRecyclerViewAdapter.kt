package com.example.shoppingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.model_or_schema.DataModel
import com.example.shoppingapp.R
import com.example.shoppingapp.db.ItemsDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ItemsRecyclerViewAdapter : RecyclerView.Adapter<ItemsRecyclerViewAdapter.ViewHolder>() {

    var items = arrayListOf<DataModel>()
    private lateinit var context: Context

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lyt, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemTv.text = items[position].item
        holder.countTv.text = items[position].count.toString()

        holder.increaseIv.setOnClickListener {
            ++items[position].count
            updateCount(position)
        }

        holder.decreaseIv.setOnClickListener {
            val count = items[position].count
            if (count > 0) {
                --items[position].count
                updateCount(position)
            }
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val itemTv: TextView = itemView.findViewById(R.id.item_tv)
        val countTv: TextView = itemView.findViewById(R.id.count_tv)
        val increaseIv: ImageView = itemView.findViewById(R.id.increase_Iv)
        val decreaseIv: ImageView = itemView.findViewById(R.id.decrease_Iv)
    }

    private fun updateCount(position: Int) {
        val database = ItemsDatabase.getDatabase(context)
        GlobalScope.launch {
            database.itemsDao().update(items[position])
        }
        notifyItemChanged(position)
    }
}
