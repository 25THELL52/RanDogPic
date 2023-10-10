package com.example.randogpic.util

import android.support.test.espresso.IdlingResource
import android.support.test.espresso.idling.CountingIdlingResource
import android.util.Log


object EspressoIdlingResource  {

     private const val RESOURCE = "GLOBAL"

    private val mCountingIdlingResource: CountingIdlingResource = CountingIdlingResource(RESOURCE)



    fun increment() {
        mCountingIdlingResource.increment()

    }

    fun decrement() {


        mCountingIdlingResource.decrement()
    }

    fun getIdlingResource(): IdlingResource? {
        return mCountingIdlingResource
    }
}

