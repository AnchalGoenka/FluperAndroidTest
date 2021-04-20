package com.anchal.aanchaltest.Activity

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.anchal.aanchaltest.Db.DatabaseHandler
import com.anchal.aanchaltest.R
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.custom_image_dialog.view.*

class ProductDetailsActivity : AppCompatActivity() {
    var dbHandler: DatabaseHandler? = null
    var productImage :Int ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        dbHandler = DatabaseHandler(this)  // get db instance
        getproduct() //get product detail based on product id

        btn_update.setOnClickListener { // update click listener
            dbHandler?.updateProduct(getIntent().getStringExtra("Id").toString())
            getproduct() // get product detail after updating db
        }

        btn_delete.setOnClickListener { // delete click listener
            dbHandler?.deleteProduct(getIntent().getStringExtra("Id").toString())
            finish()
        }

        iv_productImage.setOnClickListener {
             bigImage()
        }
    }

    fun getproduct(){
        val product = dbHandler?.getProduct(getIntent().getStringExtra("Id").toString())
        tv_productName.text = product?.name
        tv_productPrice.text = product?.mrp
        tv_productPrice.paintFlags = tv_productPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        tv_productSalePrice.text = product?.sp
        tv_desc.text = product?.desc
        tv_color.text = product?.color
        productImage = product?.image!!.toInt()
        iv_productImage.setImageResource(productImage!!)

        getStoreName(product.storeId)
    }

    fun getStoreName(storeId: String) {
        val store = dbHandler?.getStore(storeId)
        tv_storeName.text = store?.name
    }

    fun bigImage(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_image_dialog, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        //show dialog
        val  mAlertDialog = mBuilder.show()
        //close button click listener
        mDialogView.iv_close.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()

        }
        mDialogView.iv_productBigImage.setImageResource(productImage!!)
    }
}