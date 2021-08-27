package com.example.cats.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cats.R
import com.example.cats.data.FoodItem

class FoodItemAdapter(
    private val listener: OnItemClickListener
) :
    ListAdapter<FoodItem, FoodItemAdapter.FoodItemHolder>(FoodListAdapterCallback()) {

    inner class FoodItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {

        private val buttonView: Button = itemView.findViewById(R.id.food_name_bef)

        fun bind(item: FoodItem) {
            buttonView.text = item.name
        }

        init {
            buttonView.setOnClickListener(this)
            buttonView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

        override fun onLongClick(p0: View?): Boolean {
            val position = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION){
                listener.onLongItemClick(position)
            }
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.button_layout_rv, parent, false)
        return FoodItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: FoodItemHolder, position: Int) {
//        val currentItem = foodItemList[position]
//        holder.buttonView.text = currentItem.name
        holder.bind(getItem(position))
    }


    interface OnItemClickListener {

        fun onItemClick(position: Int)

        fun onLongItemClick(position: Int)
    }
}

class FoodListAdapterCallback : DiffUtil.ItemCallback<FoodItem>() {
    override fun areItemsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
        return oldItem == newItem
    }


}


