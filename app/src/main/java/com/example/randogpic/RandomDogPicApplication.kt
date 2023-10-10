package com.example.randogpic

import android.app.Application
import com.example.randogpic.data.network.RandomDogPicApi
import com.example.randogpic.data.repository.RandomDogPicRepository
import com.example.randogpic.data.repository.RandomDogPicRepositoryImpl

class RandomDogPicApplication : Application() {


    val repository : RandomDogPicRepository
        by lazy {RandomDogPicRepositoryImpl(RandomDogPicApi.create())}

}