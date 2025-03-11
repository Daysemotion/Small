package com.test.small

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.small.repository.userInfoItem

class ListAdapter (private var itemList: List<userInfoItem>) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>(){

    fun updateList(newList: List<userInfoItem>){
        itemList = newList
        notifyDataSetChanged()
    }

    override fun getItemCount() = itemList.size

        class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val tv_name: TextView = view.findViewById(R.id.tv_list_name)
            val tv_phone: TextView = view.findViewById(R.id.tv_list_phone)
            val tv_email: TextView = view.findViewById(R.id.tv_list_email)
        }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_row_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_name.text = itemList[position].name
        holder.tv_phone.text = itemList[position].phone
        holder.tv_email.text = itemList[position].email
    }

}
