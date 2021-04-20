package com.anchal.aanchaltest.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.anchal.aanchaltest.Adapter.ProductAdapter
import com.anchal.aanchaltest.Db.DatabaseHandler
import com.anchal.aanchaltest.R
import kotlinx.android.synthetic.main.activity_product_list.*


class ProductListActivity : AppCompatActivity() {
    var dbHandler: DatabaseHandler? = null
    private var productAdapter: ProductAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        dbHandler = DatabaseHandler(this) // get db instance
    }

    override fun onResume() {
        super.onResume()
        showProducts() // fetch product list from db
    }

    fun showProducts(){
        val product = dbHandler!!.getAllProduct()
        rv_productList.layoutManager = LinearLayoutManager(this)
        productAdapter = ProductAdapter(this,product){list ,i->
            val intent = Intent(this,ProductDetailsActivity::class.java)
            intent.putExtra("Id",product.get(i).Id)
            startActivity(intent)

        }
        rv_productList.adapter = productAdapter
    }
}