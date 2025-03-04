package com.test.small

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter (val itemlist: ArrayList<userInfoItem>) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>(){
        class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val tv_name: TextView
            val tv_phone: TextView
            val tv_email: TextView
            init{
                tv_name = view.findViewById(R.id.tv_list_name)
                tv_phone = view.findViewById(R.id.tv_list_phone)
                tv_email = view.findViewById(R.id.tv_list_email)
            }

        }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_name.text = itemlist[position].name
        holder.tv_phone.text = itemlist[position].phone
        holder.tv_email.text = itemlist[position].email
    }

    override fun getItemCount() = itemlist.size
}
