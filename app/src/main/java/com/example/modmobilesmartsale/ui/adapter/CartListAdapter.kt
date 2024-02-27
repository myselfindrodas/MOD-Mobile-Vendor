package com.example.modmobilecustomer.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.viewcartmodel.Cartdata
import com.example.modmobilesmartsale.data.model.getfavouritemodel.Data
import com.squareup.picasso.Picasso

class CartListAdapter(val context: Context, var onItemClickListener: CartItemClickListener) :
    RecyclerView.Adapter<CartListAdapter.classViewHolder>() {

    private val inflater: LayoutInflater
    var cartItems: ArrayList<Cartdata> = ArrayList()
    private lateinit var qtyList: Array<String>
    var favouriteItems: ArrayList<Data> = ArrayList()

    init {
        inflater = LayoutInflater.from(context)
    }

    interface CartItemClickListener {
        fun cartsItemOnClick(position: Int, list: ArrayList<Cartdata>, view: View, type:String, favlist: ArrayList<Data>)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartListAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.cart_list, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartListAdapter.classViewHolder, position: Int) {
        with(holder) {

            Picasso.get()
                .load(cartItems[position].productImgUrl.toString())
                .error(R.drawable.phone_image)
                .placeholder(R.drawable.phone_image)
                .into(ivMobileImageInCart)





            if (cartItems[position].mrp.isNullOrEmpty()) {
                tvOffInCart.text = "0 %"
            } else {
                val pricedifference = cartItems[position].mrp.toFloat()
                    .minus(cartItems[position].price.toFloat())
                val offpercent = (pricedifference.div(cartItems[position].mrp.toFloat()) * 100)
                tvOffInCart.text = String.format("%.2f", offpercent) + " %"
            }




            if (cartItems[position].productName.isNullOrEmpty()) {
                tvMobileNameInCart.text = " "
            } else {
                tvMobileNameInCart.text = cartItems[position].productName
            }



            if (cartItems[position].productQty.isNullOrEmpty()) {
                stockInCart.text = " "
            } else {
                stockInCart.text = "Quantity : " + cartItems[position].productQty
            }


            if (cartItems[position].type.isNullOrEmpty()) {
                tvMobileQualityInCart.text = " "
            } else {
                tvMobileQualityInCart.text = cartItems[position].type
            }


            tvMobileStorageInCart.text = " "


            if (cartItems[position].price.isNullOrEmpty()) {
                tvMobileOffPriceInCart.text = " "
            } else {
                tvMobileOffPriceInCart.text = "₹ " + cartItems[position].price
            }


            if (cartItems[position].mrp.isNullOrEmpty()) {
                tvMobileRealPriceInCart.text = " "
            } else {
                tvMobileRealPriceInCart.text = "₹ " + cartItems[position].mrp
            }




            tvSavePriceOnMobileInCart.text = "Save ₹ " + cartItems[position].mrp.toFloat()
                .minus(cartItems[position].price.toFloat())


            qtyList = arrayOf("1", "2", "more")
            val qtyAdapter =
                ArrayAdapter(context, R.layout.spinner_text, qtyList)
            qtySpinnerInCart.adapter = qtyAdapter

            rlAddToFavourite.setOnClickListener {

                if (tvFavourite.text == "ADD TO FAVOURITE") {
                    ivFavourite.setImageResource(R.drawable.ic_add_to_favourite)
                    tvFavourite.setText("ADDED TO FAVOURITE")
                    cartItems[position].isSelected=true
                } else {
                    ivFavourite.setImageResource(R.drawable.icon_red_heart)
                    tvFavourite.setText("ADD TO FAVOURITE")
                    cartItems[position].isSelected=false
                }
                onItemClickListener.cartsItemOnClick(position, cartItems, it, "addfav", favouriteItems)

//                val navController = Navigation.findNavController(it)
//                navController.navigate(R.id.nav_favourities)
            }


            rlRemoveFromCart.setOnClickListener {


                onItemClickListener.cartsItemOnClick(position, cartItems, it, "removeitem", favouriteItems)
//                val navController = Navigation.findNavController(it)
//                navController.navigate(R.id.nav_favourities)
            }

            if (favouriteItems.isNotEmpty()) {

                favouriteItems.forEach { favList ->
                    if (favList.imei1 == cartItems[position].productId) {
                        cartItems[position].isSelected = true
                        ivFavourite.setImageResource(R.drawable.ic_add_to_favourite)
                        tvFavourite.setText("ADDED TO FAVOURITE")
                        return
                    } else {
                        ivFavourite.setImageResource(R.drawable.icon_red_heart)
                        tvFavourite.setText("ADD TO FAVOURITE")

                        cartItems[position].isSelected = false
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }


    fun getcartItems(): ArrayList<Cartdata>{
        return cartItems
    }

    fun updateData(mFavouriteItems: List<Any>, mCartItems: List<Cartdata>) {
        favouriteItems = mFavouriteItems as ArrayList<Data>
        cartItems = mCartItems as ArrayList<Cartdata>
        notifyDataSetChanged()
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivMobileImageInCart: ImageView
        var tvOffInCart: TextView
        var tvMobileNameInCart: TextView
        var ratingNoInCart: TextView
        var stockInCart: TextView
        var tvMobileQualityInCart: TextView
        var tvMobileStorageInCart: TextView
        var tvMobileOffPriceInCart: TextView
        var tvMobileRealPriceInCart: TextView
        var tvSavePriceOnMobileInCart: TextView
        var qtySpinnerInCart: Spinner
        var rlAddToFavourite: RelativeLayout
        var rlRemoveFromCart: RelativeLayout
        var ivFavourite: ImageView
        var tvFavourite: TextView


        init {
            ivMobileImageInCart = itemView.findViewById(R.id.ivMobileImageInCart)
            tvOffInCart = itemView.findViewById(R.id.tvOffInCart)
            tvMobileNameInCart = itemView.findViewById(R.id.tvMobileNameInCart)
            ratingNoInCart = itemView.findViewById(R.id.ratingNoInCart)
            stockInCart = itemView.findViewById(R.id.stockInCart)
            tvMobileQualityInCart = itemView.findViewById(R.id.tvMobileQualityInCart)
            tvMobileStorageInCart = itemView.findViewById(R.id.tvMobileStorageInCart)
            tvMobileOffPriceInCart = itemView.findViewById(R.id.tvMobileOffPriceInCart)
            tvMobileRealPriceInCart = itemView.findViewById(R.id.tvMobileRealPriceInCart)
            tvSavePriceOnMobileInCart = itemView.findViewById(R.id.tvSavePriceOnMobileInCart)
            qtySpinnerInCart = itemView.findViewById(R.id.qtySpinnerInCart)
            rlAddToFavourite = itemView.findViewById(R.id.rlAddToFavourite)
            rlRemoveFromCart = itemView.findViewById(R.id.rlRemoveFromCart)
            ivFavourite = itemView.findViewById(R.id.ivFavourite)
            tvFavourite = itemView.findViewById(R.id.tvFavourite)
        }
    }

}