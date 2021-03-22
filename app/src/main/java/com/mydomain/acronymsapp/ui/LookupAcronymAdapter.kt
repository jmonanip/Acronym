package com.mydomain.acronymsapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mydomain.acronymsapp.R
import com.mydomain.acronymsapp.model.Acronym

class LookupAcronymAdapter : RecyclerView.Adapter<LookupAcronymAdapter.LookupViewHolder>() {
    private val lookupAcronymList = ArrayList<Acronym>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LookupViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.lookup_acronym_item, parent, false)
        return LookupViewHolder(view)
    }

    override fun onBindViewHolder(holder: LookupViewHolder, position: Int) {
        val acronym: Acronym = lookupAcronymList[position]
        holder.bind(acronym)
    }

    override fun getItemCount(): Int {
        return lookupAcronymList.size
    }

    fun addItems(newItems: List<Acronym>?) {
        lookupAcronymList.addAll(newItems!!)
        notifyDataSetChanged()
    }

    fun clearList() {
        lookupAcronymList.clear()
        notifyDataSetChanged()
    }

    inner class LookupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val meaning: TextView = itemView.findViewById(R.id.lookup_meaning)

        fun bind(acronym: Acronym) {
            meaning.text = acronym.getLForm()
        }
    }
}
