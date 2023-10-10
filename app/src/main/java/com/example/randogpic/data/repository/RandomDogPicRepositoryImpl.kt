package com.example.randogpic.data.repository

import android.util.Log
import com.example.randogpic.data.network.RandomDogPicApi
import com.example.randogpic.model.Dog
import com.example.randogpic.util.EspressoIdlingResource

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class RandomDogPicRepositoryImpl(private val api: RandomDogPicApi) : RandomDogPicRepository {


    override fun getDog(callback: RepositoryCallback) {



        Log.d("RandomDogPicRepoImpl", "sending a request to the url Thread :${Thread.currentThread().name}")

        api.getDog().enqueue(wrapCallBack(callback))


    }


    private fun wrapCallBack(callback: RepositoryCallback) =
        object : Callback<Dog> {
            override fun onResponse(call: Call<Dog>, response: Response<Dog>) {

                if (response != null && response.isSuccessful) {
                    val dog = response.body()
                    if (dog != null) {
                        callback.onSuccess(dog)
                        Log.d("RandomDogPicRepoImpl", "inside OnResponse Repository Thread :${Thread.currentThread().name}")

                        return
                    }
                    callback.onError("Couldn't get dog object")
                }
            }

            override fun onFailure(call: Call<Dog>, t: Throwable) {
                callback.onError("Couldn't get dog object")
            }


        }


}
