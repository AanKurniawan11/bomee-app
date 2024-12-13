package com.example.bomeeapp.ui.cs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.bomeeapp.R
import com.example.bomeeapp.data.network.model.CsModel

class CsAdapter(private val csItems: List<CsModel>) :
    RecyclerView.Adapter<CsAdapter.CsViewHolder>() {

    private val expandedPosition = mutableListOf<Int>()


    class CsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_help_title)
        val description: TextView = itemView.findViewById(R.id.tv_help_description)
        val arrow: ImageView = itemView.findViewById(R.id.iv_arrow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cs_item, parent, false)
        return CsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CsViewHolder, position: Int) {
        val helpItem = csItems[position]

        holder.title.text = helpItem.title
        holder.description.text = helpItem.description

        // Set visibility based on the expanded positions
        if (expandedPosition.contains(position)) {
            holder.description.visibility = View.VISIBLE
            holder.arrow.setImageResource(R.drawable.baseline_expand_less_24) // Change icon to 'less'
        } else {
            holder.description.visibility = View.GONE
            holder.arrow.setImageResource(R.drawable.baseline_expand_more_24) // Change icon to 'more'
        }

        // Make the entire item clickable
        holder.itemView.setOnClickListener {
            val adapterPosition = holder.adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                if (expandedPosition.contains(adapterPosition)) {
                    expandedPosition.remove(adapterPosition) // Collapse if already expanded
                } else {
                    expandedPosition.add(adapterPosition) // Expand new item
                }
                notifyItemChanged(adapterPosition) // Notify adapter to refresh this specific item
            }
        }
    }

    override fun getItemCount(): Int {
        return csItems.size
    }

}