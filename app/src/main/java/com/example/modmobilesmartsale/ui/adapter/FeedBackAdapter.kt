package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.FeedBackData
import com.example.modmobilesmartsale.data.model.reviewlistmodel.Data


class FeedBackAdapter(val context: Context, var onItemClickListener: FeedBackListOnItemClickListener)
    :RecyclerView.Adapter<FeedBackAdapter.classViewHolder>(){

    private val inflater : LayoutInflater
    var feedbackList: ArrayList<Data> = arrayListOf()

    init {
        inflater = LayoutInflater.from(context)
    }

    interface FeedBackListOnItemClickListener {
        fun FeedbackOnClick(position: Int, list: ArrayList<FeedBackData>, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): classViewHolder {
        val view = inflater.inflate(R.layout.feedback_list, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: classViewHolder, position: Int) {
        with(holder){
            imgUser.setImageResource(R.drawable.user)

            if (feedbackList[position].name.isNullOrEmpty()) {
                tvUserNameInFeedback.text = " "
            } else {
                tvUserNameInFeedback.text = feedbackList[position].name
            }
            if (feedbackList[position].review.isNullOrEmpty()) {
                tvFeedbackMessage.text = " "
            } else {
                tvFeedbackMessage.text = feedbackList[position].review
            }

            ratingBarInFeedback.rating = feedbackList[position].rate.toFloat()
        }
    }

    fun updateData(mFeedbackList: List<Data>) {
        feedbackList = mFeedbackList as ArrayList<Data>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return feedbackList.size
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgUser: ImageView
        var tvUserNameInFeedback: TextView
        var tvFeedbackMessage: TextView
        var ratingBarInFeedback:RatingBar

        init {
            imgUser = itemView.findViewById(R.id.imgUser)
            tvUserNameInFeedback = itemView.findViewById(R.id.tvUserNameInFeedback)
            tvFeedbackMessage = itemView.findViewById(R.id.tvFeedbackMessage)
            ratingBarInFeedback = itemView.findViewById(R.id.ratingBarInFeedback)
        }
    }

}