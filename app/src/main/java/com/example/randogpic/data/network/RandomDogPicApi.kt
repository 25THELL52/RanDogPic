package com.example.randogpic.data.network

import com.example.randogpic.model.Dog
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RandomDogPicApi {


    @GET("random")
    fun getDog(): Call<Dog>


    companion object Factory {
        fun create(): RandomDogPicApi {
            val gson = GsonBuilder().create()


            val retrofit = Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/breeds/image/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(RandomDogPicApi::class.java)
        }
    }}