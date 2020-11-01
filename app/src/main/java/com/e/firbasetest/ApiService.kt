package com.e.firbasetest

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

object ApiService {
    const val BASE_URL = "https://test-backend-9ab18.firebaseio.com/"

    fun getDataApi(): DataApi{

        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(DataApi::class.java)
    }

    interface DataApi{
        @GET("user.json?print=pretty")
        fun getUserData(): retrofit2.Call<User>

        @GET("personal.json?print=pretty")
        fun getPersonalData(): retrofit2.Call<Personal>
    }
}

