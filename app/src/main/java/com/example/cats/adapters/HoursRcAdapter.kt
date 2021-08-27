package com.example.cats.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cats.data.Hours
import com.example.cats.data.Minutes
import com.example.cats.R


class HoursAdapter(private val hourslist: List<Hours>) :
    RecyclerView.Adapter<HoursAdapter.HoursHolder>() {

    class HoursHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.hours)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoursHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.time_setting_hours_recycler, parent, false)
        return HoursHolder(itemView)
    }

    override fun onBindViewHolder(holder: HoursHolder, position: Int) {
        val currentItem = hourslist[position]
        holder.textView.text = currentItem.hours.toString()
    }

    override fun getItemCount() = hourslist.size

}

class MinutesAdapter(private val minutesList: List<Minutes>) :
    RecyclerView.Adapter<MinutesAdapter.MinutesHolder>() {
    class MinutesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.hours)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MinutesHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.time_setting_hours_recycler, parent, false)
        return MinutesHolder(itemView)
    }

    override fun onBindViewHolder(holder: MinutesHolder, position: Int) {
        val currentItem = minutesList[position]
        holder.textView.text = currentItem.minutes.toString()
    }

    override fun getItemCount() = minutesList.size
}