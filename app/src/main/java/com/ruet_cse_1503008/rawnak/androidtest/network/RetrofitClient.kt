package com.ruet_cse_1503008.rawnak.androidtest.network

import com.google.gson.GsonBuilder
//import com.ihsanbal.logging.Level
//import com.ihsanbal.logging.LoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var retrofit: Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()

    val client: Retrofit
        get() {
            if (retrofit == null) {
                synchronized(Retrofit::class.java) {
                    if (retrofit == null) {

//                        val httpClient = OkHttpClient.Builder()
//                            .addInterceptor(QueryParameterAddInterceptor())
//                            // for pretty log of HTTP request-response
//                            .addInterceptor(
//                                LoggingInterceptor.Builder()
//                                    .loggable(BuildConfig.DEBUG)
//                                    .setLevel(Level.BASIC)
//                                    .log(Platform.INFO)
//                                    .request("LOG")
//                                    .response("LOG")
//                                    .executor(Executors.newSingleThreadExecutor())
//                                    .build()
//                            )
//
//                        val client = httpClient.build()

                        retrofit = Retrofit.Builder()
                            .baseUrl("https://picsum.photos/v2/")
                            .addConverterFactory(GsonConverterFactory.create(gson))
//                            .client(client)
                            .build()
                    }
                }

            }
            return retrofit!!
        }
}