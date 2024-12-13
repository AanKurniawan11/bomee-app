//package com.example.bomeeapp.ui.booking
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ArrayAdapter
//import android.widget.BaseAdapter
//import android.widget.TextView
//import androidx.annotation.LayoutRes
//import com.example.bomeeapp.R
//import com.example.bomeeapp.data.network.responses.booking.room.RoomData
//
//class BookingAdapter(
//    private val context: Context,
//    private val rooms: List<RoomData>,
//    ) : BaseAdapter() {
//
//    override fun getCount(): Int = rooms.size
//
//    override fun getItem(position: Int): RoomData = rooms[position]
//
//    override fun getItemId(position: Int): Long = position.toLong()
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_room_spinner, parent, false)
//        val room = getItem(position)
//        val roomName = view.findViewById<TextView>(R.id.spinner1)
//        roomName.text = room.name
//        return view
//    }
//
//    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_room_spinner_dropdown, parent, false)
//        val room = getItem(position)
//        val roomName = view.findViewById<TextView>(R.id.spinner1)
//        roomName.text = room.name
//        return view
//    }
//}