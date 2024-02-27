package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.stockmodel.Data
import com.squareup.picasso.Picasso

class SearchAdapter(val context: Context, var onItemClickListener: SearchItemClickListener)
    :RecyclerView.Adapter<SearchAdapter.classViewHolder>() {

    private val inflater : LayoutInflater
    var searchList: ArrayList<Data> = ArrayList()

    init {
        inflater = LayoutInflater.from(context)
    }

    interface SearchItemClickListener {
        fun searchListOnClick(position: Int, list: ArrayList<Data>, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.search_list2, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchAdapter.classViewHolder, position: Int) {
        with(holder){
            if (searchList[position].modelName.isNullOrEmpty()) {
                searchText.text = " "
            } else {
                searchText.text = searchList[position].modelName
            }

            if (position==searchList.size-1){
                line.visibility = View.GONE
            }else{
                line.visibility = View.VISIBLE
            }

            Picasso.get()
                .load(searchList[position].originalImage1)
                .error(R.drawable.phone_image)
                .placeholder(R.drawable.phone_image)
                .into(imgPhone)

            itemView.setOnClickListener {
                onItemClickListener.searchListOnClick(position, searchList, it)

            }
        }
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    fun updateData( mSearchList: List<Data>) {
        searchList = mSearchList as ArrayList<Data>
        notifyDataSetChanged()
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var searchText: TextView
        var imgPhone: ImageView
        var line: View
        init {
            searchText = itemView.findViewById(R.id.searchText)
            imgPhone = itemView.findViewById(R.id.imgPhone)
            line = itemView.findViewById(R.id.line)
        }
    }
}