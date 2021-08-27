package com.example.cats.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cats.R
import com.example.cats.data.FedData

class TestAdapter : ListAdapter<FedData, TestAdapter.TestAdapterViewHolder>(ListAdapterCallback()) {

    class TestAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val textView1: TextView = itemView.findViewById(R.id.feed_time)
        private val textView2: TextView = itemView.findViewById(R.id.food_name)

        fun bind(item: FedData) = with(itemView){

            textView1.text = item.feedTime
            textView2.text = item.foodName
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestAdapterViewHolder {
        return TestAdapterViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_feed, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TestAdapterViewHolder, position: Int) {
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.recycler_anim)
        holder.bind(getItem(position))
    }
}

class ListAdapterCallback : DiffUtil.ItemCallback<FedData>() {
    override fun areItemsTheSame(oldItem: FedData, newItem: FedData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FedData, newItem: FedData): Boolean {
       return oldItem == newItem
    }

}

