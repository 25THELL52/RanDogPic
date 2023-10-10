package com.example.randogpic.data.repository

import com.example.randogpic.data.network.RandomDogPicApi
import com.example.randogpic.model.Dog
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class RandomDogPicRepositoryImplTest {

    @Mock
    lateinit var repositoryCallback: RepositoryCallback

    @Mock
    lateinit var api: RandomDogPicApi

    @Mock
    lateinit var call: Call<Dog>

    private lateinit var randomDogPicRepositoryImpl: RandomDogPicRepositoryImpl
    private lateinit var dog: Dog

    @Before
    fun setUp() {
        randomDogPicRepositoryImpl = RandomDogPicRepositoryImpl(api)
        dog = Dog("url", "success")
    }

    @Test
    fun whenResponseIsNotNullAndSuccessfulAndBodyNotNull_OnSuccessIsCalledOnCallback() {

        whenever(api.getDog()).thenReturn(call)

        doAnswer {

            val callback: Callback<Dog> = it.getArgument(0)
            callback.onResponse(call, Response.success(dog))

        }.whenever(call).enqueue(any())

        randomDogPicRepositoryImpl.getDog(repositoryCallback)
        verify(repositoryCallback).onSuccess(dog)


    }

    @Test
    fun whenResponseIsNotNullAndSuccessfulAndBodyNull_OnErrorIsCalledOnCallback() {

        whenever(api.getDog()).thenReturn(call)

        doAnswer {

            val callback: Callback<Dog> = it.getArgument(0)
            callback.onResponse(call, Response.success(null))

        }.whenever(call).enqueue(any())

        randomDogPicRepositoryImpl.getDog(repositoryCallback)
        verify(repositoryCallback).onError("Couldn't get dog object")


    }

    @Test
    fun whenResponseHasFailed_OnErrorIsCalledOnCallback() {

        whenever(api.getDog()).thenReturn(call)
        doAnswer {

            val callback: Callback<Dog> = it.getArgument(0)
            callback.onFailure(call, Throwable())

        }.whenever(call).enqueue(any())

        randomDogPicRepositoryImpl.getDog(repositoryCallback)
        verify(repositoryCallback).onError("Couldn't get dog object")


    }
}