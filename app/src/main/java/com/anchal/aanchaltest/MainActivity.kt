package com.anchal.aanchaltest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.anchal.aanchaltest.Activity.ProductListActivity
import com.anchal.aanchaltest.Db.DatabaseHandler
import com.anchal.aanchaltest.Model.ProductModel
import com.anchal.aanchaltest.Model.StoreModel
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    companion object{
        var dbHandler: DatabaseHandler? = null
        var productCount = 0
        var productList = ArrayList<JSONObject>()
        val imagelist = arrayOf(R.drawable.chili_sauce,R.drawable.ice_cream,R.drawable.chips,R.drawable.football)
        val productName = arrayOf("Chili Sauce","Ice Cream","Chips","Football")
        val productColor = arrayOf("Red", "White", "Yellow", "White&Black")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.deleteDatabase("ProductDatabase") // reset database

        dbHandler = DatabaseHandler(this)

        // creation of mock data till 3 count
        for( i in 0..3) {

            val store = JSONObject()
            store.put("id", "10" + i)
            store.put("name", productName[i] + " Store")

            val product = JSONObject()
            product.put("id", "00"+i)
            product.put("name", productName[i])
            product.put("desc", "This is product description  i.e.,"+productName[i] + "\nLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
            product.put("image",imagelist[i])
            product.put("mrp", "40$i")
            product.put("sp", "25${i+6}")
            product.put("store", store)
            product.put("color", productColor[i])
            productList.add(product)
        }

        // create product button listener
        btn_create_product.setOnClickListener {

               if(productCount<=3){ // check till 3 count as we have only 4 product mock data

                   val store = StoreModel()

                   val json = productList[productCount].getJSONObject("store")

                   store.id = json["id"] as String
                   store.name = json["name"] as String

                   dbHandler!!.insertStore(store)

                   val product = ProductModel()

                   product.Id = productList[productCount].get("id").toString()
                   product.name = productList[productCount].get("name").toString()
                   product.mrp = productList[productCount].get("mrp").toString()
                   product.sp = productList[productCount].get("sp").toString()
                   product.image = productList[productCount].get("image").toString()
                   product.desc = productList[productCount].get("desc").toString()
                   product.color = productList[productCount].get("color").toString()
                   product.storeId = json["id"] as String

                   Log.d("Database", "id =" + product.storeId)

                   dbHandler!!.insertProduct(product)

                   productCount ++
                   Toast.makeText(this,"Product created successfully",Toast.LENGTH_LONG).show()

               } else { // if count exceeds 3 then ggive alert
                   Toast.makeText(this,"Product addition limit exceed",Toast.LENGTH_LONG).show()
               }
        }

        // show product button click listener
       btn_show_product.setOnClickListener {
           val intent = Intent(this,ProductListActivity::class.java)
           startActivity(intent)
       }

    }
}