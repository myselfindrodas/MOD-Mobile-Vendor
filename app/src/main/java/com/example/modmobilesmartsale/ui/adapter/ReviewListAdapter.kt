package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.ReviewListData

class ReviewListAdapter(val context: Context, var onItemClickListener: ReviewItemClickListener)
    : RecyclerView.Adapter<ReviewListAdapter.classViewHolder>(){

    private val inflater : LayoutInflater
    var reviewList: ArrayList<ReviewListData> = arrayListOf()

    init {
        inflater = LayoutInflater.from(context)
    }

    interface ReviewItemClickListener {
        fun ReviewItemOnClick(position: Int, list: ArrayList<ReviewListData>, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewListAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.review_list, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewListAdapter.classViewHolder, position: Int) {
        with(holder){
            if (reviewList[position].ivUserImage==null) {
                ivUserImage.setImageResource(R.drawable.user)
            } else {
                ivUserImage.setImageResource(reviewList[position].ivUserImage)
            }
            if (reviewList[position].tvUserNameInReview.isNullOrEmpty()) {
                tvUserNameInReview.text = " "
            } else {
                tvUserNameInReview.text = reviewList[position].tvUserNameInReview
            }
            if (reviewList[position].tvReview.isNullOrEmpty()) {
                tvReview.text = " "
            } else {
                tvReview.text = reviewList[position].tvReview
            }
            if (reviewList[position].mobileImage==null) {
                mobileImage.visibility = View.GONE
            } else {
                mobileImage.setImageResource(reviewList[position].mobileImage)
            }
            if (reviewList[position].tvReviewDate.isNullOrEmpty()) {
                tvReviewDate.text = " "
            } else {
                tvReviewDate.text = reviewList[position].tvReviewDate
            }
        }
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    fun updateData(mReviewList: List<ReviewListData>) {
        reviewList = mReviewList as ArrayList<ReviewListData>
        notifyDataSetChanged()
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivUserImage: ImageView
        var tvUserNameInReview: TextView
        var tvReview: TextView
        var mobileImage: ImageView
        var tvReviewDate: TextView

        init {
            ivUserImage = itemView.findViewById(R.id.ivUserImage)
            tvUserNameInReview = itemView.findViewById(R.id.tvUserNameInReview)
            tvReview = itemView.findViewById(R.id.tvReview)
            mobileImage = itemView.findViewById(R.id.mobileImage)
            tvReviewDate = itemView.findViewById(R.id.tvReviewDate)
        }
    }

}