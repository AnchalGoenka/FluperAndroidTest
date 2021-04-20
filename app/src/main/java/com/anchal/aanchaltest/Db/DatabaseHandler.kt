package com.anchal.aanchaltest.Db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.anchal.aanchaltest.Model.ProductModel
import com.anchal.aanchaltest.Model.StoreModel


class DatabaseHandler(context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "ProductDatabase"
        private val TABLE_NAME = "ProductTable"
        private val Product_ID = "id"
        private val Product_NAME = "name"
        private val Product_MRP = "mrp"
        private val Product_sp = "salePrice"
        private val Product_Image = "image"
        private val Product_Description = "desc"
        private val Product_Color = "color"

        private val STORE_TABLE_NAME = "StoreTable"
        private val Store_Id = "id"
        private val Store_Name = "name"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        // create store table in db
        val CREATE_STORE_TABLE =
            ("CREATE TABLE $STORE_TABLE_NAME ($Store_Id Text PRIMARY KEY , $Store_Name Text)")
        db?.execSQL(CREATE_STORE_TABLE)

        // creates product table in db
        val CREATE_PRODUCT_TABLE = ("CREATE TABLE $TABLE_NAME" +
                "($Product_ID Text PRIMARY KEY , $Product_NAME TEXT , $Product_MRP Text , $Product_Image Text , $Product_sp text , $Product_Description Text, $Product_Color Text, FOREIGN KEY ($Store_Id) REFERENCES $STORE_TABLE_NAME ($Store_Id))")

        db?.execSQL(CREATE_PRODUCT_TABLE)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        p0?.execSQL("DROP TABLE IF EXISTS $STORE_TABLE_NAME")
        onCreate(p0)
    }

    // add store in db
    fun insertStore(store: StoreModel) {
        val db = writableDatabase
        val value = ContentValues()
        value.put(Store_Id, store.id)
        value.put(Store_Name, store.name)

        db.insert(STORE_TABLE_NAME, null, value)
        db.close()
    }

    // add product in db
    fun insertProduct(product: ProductModel) {
        val db = writableDatabase
        val value = ContentValues()
        value.put(Product_ID, product.Id)
        value.put(Product_NAME, product.name)
        value.put(Product_MRP, product.mrp)
        value.put(Product_sp, product.sp)
        value.put(Product_Image, product.image)
        value.put(Product_Description, product.desc)
        value.put(Product_Color, product.color)
        value.put(Store_Id, product.storeId)

        db.insert(TABLE_NAME, null, value)
        db.close()
    }

    // get store by id
    fun getStore(id: String): StoreModel {
        val db = readableDatabase
        val store = StoreModel()
        val selectQuery = "SELECT * FROM $STORE_TABLE_NAME WHERE $Store_Id = '$id'"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    store.id = cursor.getString(cursor.getColumnIndex(Store_Id))
                    store.name = cursor.getString(cursor.getColumnIndex(Store_Name))
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return store
    }

   //get product by id
    fun getProduct(_id: String): ProductModel? {
       val db = readableDatabase
       val product = ProductModel()
       val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $Product_ID = '$_id'"
       val cursor = db.rawQuery(selectQuery, null)

       if (cursor != null) {

           if (cursor.moveToFirst()) {
               do {

                   product.Id = cursor.getString(cursor.getColumnIndex(Product_ID))
                   product.name = cursor.getString(cursor.getColumnIndex(Product_NAME))
                   product.image = cursor.getString(cursor.getColumnIndex(Product_Image))
                   product.mrp = cursor.getString(cursor.getColumnIndex(Product_MRP))
                   product.sp = cursor.getString(cursor.getColumnIndex(Product_sp))
                   product.desc = cursor.getString(cursor.getColumnIndex(Product_Description))
                   product.color = cursor.getString(cursor.getColumnIndex(Product_Color))
                   product.storeId = cursor.getString(cursor.getColumnIndex(Store_Id))

               } while (cursor.moveToNext())
           }

       }
       cursor.close()
       db.close()
       return product
    }

     // get all products
    fun getAllProduct():ArrayList<ProductModel> {
             val list = ArrayList<ProductModel>()

             val db = readableDatabase
             val selectALLQuery = "SELECT * FROM $TABLE_NAME"
             val cursor = db.rawQuery(selectALLQuery, null)
             if (cursor != null) {
                if (cursor.moveToFirst()) {
                 do {
                    val product = ProductModel()
                    product.Id = cursor.getString(cursor.getColumnIndex(Product_ID))
                    product.name = cursor.getString(cursor.getColumnIndex(Product_NAME))
                    product.mrp = cursor.getString(cursor.getColumnIndex(Product_MRP))
                    product.sp = cursor.getString(cursor.getColumnIndex(Product_sp))
                    product.image = cursor.getString(cursor.getColumnIndex(Product_Image))

                    list.add(product)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
      return   list
    }

    //update product
    fun updateProduct( ID: String) {
        val sqLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(Product_MRP, "1000")
        values.put(Product_sp,"600")
        //updating row
        sqLiteDatabase.update(TABLE_NAME, values, "ID=$ID", null)
        sqLiteDatabase.close()
    }

    //delete the product
    fun deleteProduct(ID: String) {
        val sqLiteDatabase = this.writableDatabase
        //deleting row
        sqLiteDatabase.delete(TABLE_NAME, "ID=$ID", null)
        sqLiteDatabase.close()
    }
}