package com.anchal.aanchaltest.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anchal.aanchaltest.Model.ProductModel
import com.anchal.aanchaltest.R

class ProductAdapter(val context :Context,val productlist:ArrayList<ProductModel>,val itemclick: (ProductModel, Int) -> Unit) : RecyclerView.Adapter<ProductAdapter.ViewHolder>(){
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        val prodName = itemView.findViewById(R.id.tv_productName) as TextView
        val prodPrice = itemView.findViewById(R.id.tv_productPrice) as TextView
        val prodSalePrice = itemView.findViewById(R.id.tv_productSalePrice) as TextView
        val image = itemView.findViewById<ImageView>(R.id.iv_productImage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=
            LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return productlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list= productlist.get(position)
        holder.prodName.text=list.name
        holder.prodPrice.text=list.mrp
        holder.prodSalePrice.text=list.sp
        val s :Int = list.image.toInt()
        holder.image.setImageResource(s)
        holder.image.setOnClickListener {
            itemclick(list,position)
        }
    }

}