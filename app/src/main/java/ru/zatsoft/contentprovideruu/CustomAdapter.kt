package ru.zatsoft.contentprovideruu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val contactList: MutableList<ContactModel>):
    RecyclerView.Adapter<CustomAdapter.ContactsViewHolder>() {

    private var onContactClickListener: OnContactClickListener? =null

    interface OnContactClickListener{
        fun onContactClick(contact:  ContactModel, position: Int)
    }

    class ContactsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.item_name)
        val phone: TextView = itemView.findViewById(R.id.item_phone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ContactsViewHolder(itemView)
    }

    override fun getItemCount() = contactList.size

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact = contactList[position]
        holder.name.text = contact.name
        holder.phone.text = contact.phone
        holder.itemView.setOnClickListener {
            if(onContactClickListener != null){
                onContactClickListener!!.onContactClick (contact,position)
            }
        }
    }

    fun setOnContactClickListener(onContactClickListener: OnContactClickListener){
        this.onContactClickListener = onContactClickListener
    }
}