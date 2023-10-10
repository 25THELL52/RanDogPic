package com.example.randogpic.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.randogpic.data.repository.RandomDogPicRepository
import com.example.randogpic.data.repository.RandomDogPicRepositoryImpl
import com.example.randogpic.data.repository.RepositoryCallback
import com.example.randogpic.model.Dog
import com.example.randogpic.util.EspressoIdlingResource


open class RandomDogPicViewModel(private val repository: RandomDogPicRepository) : ViewModel() {


    private val loadingLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<Boolean>()
    private val dog = MutableLiveData<Dog>()


    fun getLoading(): LiveData<Boolean> = loadingLiveData
    fun getError(): LiveData<Boolean> = errorLiveData
    fun getDog(): LiveData<Dog> = dog


    fun initialize() {

        fetch()

    }

    fun fetch() {
        EspressoIdlingResource.increment()
        Log.d("MainActivity","isIdleNow() ${EspressoIdlingResource.getIdlingResource()?.isIdleNow}")


        loadingLiveData.postValue(true)
        errorLiveData.postValue(false)


        repository.getDog(object : RepositoryCallback {
            override fun onSuccess(dog: Dog) {

                Log.d("RandomDPViewModel","inside onSuccess")
                loadingLiveData.postValue(false)
                errorLiveData.postValue(false)
                this@RandomDogPicViewModel.dog.postValue(dog)

                Log.d("MainActivity","GREEN LIGHT FOR BUTTON CLICK")



            }

            override fun onError(e: String) {
                super.onError(e)
                loadingLiveData.postValue(false)
                errorLiveData.postValue(true)
                EspressoIdlingResource.decrement()
                Log.d("MainActivity","isIdleNow() ${EspressoIdlingResource.getIdlingResource()?.isIdleNow}")


                Log.d("RandomDPViewModel","inside onError")

            }


        })
    }


}