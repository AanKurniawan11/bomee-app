package com.example.bomeeapp.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bomeeapp.R
import com.example.bomeeapp.data.network.responses.booking.history_all.AllHistory
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryAllAdapter : RecyclerView.Adapter<HistoryAllAdapter.ViewHolder>() {

    private var historyAll: MutableList<AllHistory> = mutableListOf()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val roomTextView: TextView = itemView.findViewById(R.id.room_name)
        val divisiTextView: TextView = itemView.findViewById(R.id.divisi_name)
        val timeTextView: TextView = itemView.findViewById(R.id.tv_time)
        val descTextView: TextView = itemView.findViewById(R.id.desc)
        val statusTextView: TextView = itemView.findViewById(R.id.tv_status)
        val dateTextView: TextView = itemView.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history_all, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = historyAll[position]

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH.mm", Locale.getDefault())
        val startTimeFormatted = try {
            inputFormat.parse(history.startTime)?.let { outputFormat.format(it) } ?: ""
        } catch (e: Exception) {
            ""
        }

        val endTimeFormatted = try {
            inputFormat.parse(history.endTime)?.let { outputFormat.format(it) } ?: ""
        } catch (e: Exception) {
            ""
        }

        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("dd MMM yy", Locale.getDefault())
        val formattedDate = try {
            inputDateFormat.parse(history.date)?.let { outputDateFormat.format(it) } ?: ""
        } catch (e: Exception) {
            ""
        }

        val statusText = when {
            history.isApproved -> "Approved"
            history.isRejected -> "Rejected"
            history.isCanceled -> "Canceled"
            else -> "Pending"
        }

        holder.roomTextView.text = history.roomName
        holder.divisiTextView.text = history.divisions.joinToString(", ")
        holder.timeTextView.text = "$startTimeFormatted - $endTimeFormatted"
        holder.descTextView.text = history.description
        holder.statusTextView.text = statusText
        holder.dateTextView.text = formattedDate
        val cancelButton = holder.itemView.findViewById<Button>(R.id.btn_cancel)
        if (history.isCanceled || history.isApproved) {
            cancelButton.visibility = View.GONE
        } else {
            cancelButton.visibility = View.VISIBLE
            cancelButton.setOnClickListener {
                onCancelClickListener?.invoke(history.id)
            }
        }

    }

    override fun getItemCount(): Int = historyAll.size

    private var onCancelClickListener: ((String) -> Unit)? = null

    fun setOnCancelClickListener(listener: (String) -> Unit) {
        onCancelClickListener = listener
    }

    fun setData(newData: List<AllHistory>) {
        historyAll.clear()
        historyAll.addAll(newData)
        notifyDataSetChanged()
    }
}
