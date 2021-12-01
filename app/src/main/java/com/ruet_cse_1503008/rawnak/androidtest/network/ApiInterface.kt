package com.rawnak.weatherappmvvm.network

import com.ruet_cse_1503008.rawnak.androidtest.network.MyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("list")
    fun callApiForInfo(@Query("page") page: Int, @Query("limit") limit: Int): Call<List<MyResponse>>
}