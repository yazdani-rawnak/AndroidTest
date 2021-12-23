package com.ruet_cse_1503008.rawnak.androidtest

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ruet_cse_1503008.rawnak.androidtest.network.ApiInterface
import com.ruet_cse_1503008.rawnak.androidtest.network.RetrofitClient
import com.ruet_cse_1503008.rawnak.androidtest.network.MyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ruet_cse_1503008.rawnak.androidtest.recyclerview.CustomAdapter
import com.ruet_cse_1503008.rawnak.androidtest.recyclerview.ItemsViewModel

class MainActivity : AppCompatActivity() {
    var imageView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        imageView = findViewById(R.id.imageView)

        if (createOrLoadSharedPrefData() == 0) {
            // retrofit is giving the implementation of the interface
            val apiInterface: ApiInterface = RetrofitClient.client.create(ApiInterface::class.java)
            // interface implementation's method is returning a response object
            val call: Call<List<MyResponse>> = apiInterface.callApiForInfo(1, 50)

            call.enqueue(object : Callback<List<MyResponse>> {

                // if retrofit network call is successful, this method will be triggered
                override fun onResponse(
                    call: Call<List<MyResponse>>,
                    response: Response<List<MyResponse>>
                ) {
//                    for (i in 0..49) {
//                        val id = response.body()?.get(i)?.id
//                        val author = response.body()?.get(i)?.author
//                        println("response loop${i + 1} $id $author ${response.body()?.size}")
//                    }
//                    -----------------------------------------------------
//                    object to json
//                    val student = Student("Alex", "Rome")
                    val jsonString = Gson().toJson(response.body())
                    println("responsePrint $jsonString")
//                    -----------------------------------------------------
                    saveSharedPrefData(jsonString)
//                    editRecyclerView()

                    supportFragmentManager.beginTransaction()
                        .add(R.id.fragmentContainerView, GalleryFragment2())
                        .commit()

//                    Glide.with(applicationContext).load(response.body()?.get(0)?.downloadUrl).into(imageView!!)
                }

                // this method will be triggered if network call failed
                override fun onFailure(call: Call<List<MyResponse>>, t: Throwable) {
//                callback.onRequestFailed(t.localizedMessage!!) //let presenter know about failure
                }
            })
//            println("response ${createOrLoadSharedPrefData()}")
        } else {
//            getJsonData()
            Log.d("my_json_response_log", getJsonData().toString())
//            Glide.with(applicationContext).load(getJsonData().get(0).downloadUrl).into(imageView!!)
//            editRecyclerView()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainerView, GalleryFragment2())
                .commit()
        }
    }

    fun editRecyclerView() {
        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
//        val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
//        for (i in 1..20) {
//            data.add(ItemsViewModel(i, "Item " + i))
//        }

        // This will pass the ArrayList to our Adapter
//        val adapter = CustomAdapter(data)
        var list = getJsonData()
        val adapter = CustomAdapter(
            list,
            applicationContext,
            object : CustomAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    Toast.makeText(
                        applicationContext,
                        (position + 1).toString().plus(" NO. image"),
                        Toast.LENGTH_LONG
                    ).show()
                    val clickedItem = list[position]
                    clickedItem.author = "Clicked"
//                    adapter.notifyItemChanged(position)
                    recyclerview.adapter?.notifyItemChanged(position)
                }
            })

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }

    private fun createOrLoadSharedPrefData(): Int {
        val PREFS_NAME = "my_shared_prefs"
        val sharedPref: SharedPreferences =
            applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPref.getInt("my_key", 0)
    }

    fun getJsonData(): List<MyResponse> {
        val PREFS_NAME = "my_shared_prefs"
        val sharedPref: SharedPreferences =
            applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val jsonString = sharedPref.getString("my_json_data", "")
        //-----------------------------------------------------
        // json to object
        //  raw string

        val jsonObjectType = object : TypeToken<List<MyResponse>>() {}.type
        val response: List<MyResponse> = Gson().fromJson(jsonString, jsonObjectType)
        println("student $response")
        //-----------------------------------------------------
        return response
    }

    fun saveSharedPrefData(jsonString: String) {
        val PREFS_NAME = "my_shared_prefs"
        val sharedPref: SharedPreferences =
            applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt("my_key", 1)
        editor.putString("my_json_data", jsonString)
        editor.apply()
    }
}