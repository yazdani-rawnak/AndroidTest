package com.ruet_cse_1503008.rawnak.androidtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ruet_cse_1503008.rawnak.androidtest.network.ApiInterface
import com.ruet_cse_1503008.rawnak.androidtest.network.RetrofitClient
import com.ruet_cse_1503008.rawnak.androidtest.network.MyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                for(i in 0..49){
                    val id = response.body()?.get(i)?.id
                    val author = response.body()?.get(i)?.author
                    println("response loop${i+1} $id $author ${response.body()?.size}")
                }
            }

            // this method will be triggered if network call failed
            override fun onFailure(call: Call<List<MyResponse>>, t: Throwable) {
//                callback.onRequestFailed(t.localizedMessage!!) //let presenter know about failure
            }
        })
    }
}