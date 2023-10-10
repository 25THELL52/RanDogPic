package com.example.randogpic

import android.os.Bundle

import android.util.Log
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.randogpic.data.repository.RandomDogPicRepository
import com.example.randogpic.databinding.ActivityMainBinding
import com.example.randogpic.model.Dog
import com.example.randogpic.testHooks.IdlingEntity
import com.example.randogpic.util.EspressoIdlingResource
import com.example.randogpic.util.ImageLoaderImpl
import com.example.randogpic.viewModel.RandomDogPicViewModel
import com.example.randogpic.viewModel.RandomDogPicViewModelFactory


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: RandomDogPicViewModel
    lateinit var imageLoader: ImageLoaderImpl
    lateinit var repository: RandomDogPicRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        repository = (application as RandomDogPicApplication).repository

        viewModel = ViewModelProvider(
            this,
            RandomDogPicViewModelFactory(repository)
        )[RandomDogPicViewModel::class.java]

        setUpObservers()
        imageLoader = ImageLoaderImpl()


        binding.fetchBtn.setOnClickListener {
            viewModel.fetch()
        }
        viewModel.initialize()


    }


    fun displayDogPic(it: Dog) {

        Log.d("MainActivity", "url : ${it.message}  Thread :${Thread.currentThread().name}")


        imageLoader.load(this, it.message, binding.dogImageImgv)

        Log.d("MainActivity", "Image loaded ")

    }

    private fun showError(it: Boolean) {


        binding.errorContainer.root.visibility = if (it) View.VISIBLE else View.INVISIBLE


        Log.d(
            "MainActivity",
            "inside showError($it) errorVisibility = ${binding.errorContainer.root.isVisible}" +
                    "Thread :${Thread.currentThread().name}"
        )


    }

    private fun showLoading(it: Boolean) {


        binding.loadingContainer.root.visibility = if (it) View.VISIBLE else View.INVISIBLE

        Log.d(
            "MainActivity",
            "inside showLoading($it) errorVisibility = ${binding.errorContainer.root.isVisible}" +
                    "Thread :${Thread.currentThread().name}"
        )


    }

    fun setUpObservers() {
        viewModel.getLoading().observe(this, Observer {
            showLoading(it)

            Log.d("MainActivity", "Thread : ${Thread.currentThread().name}")

            Log.d("MainActivity", "showLoading($it)")

        })
        viewModel.getError().observe(this, Observer {
            showError(it)

            Log.d("MainActivity", "showError($it)")

        })


        viewModel.getDog().observe(this, Observer {
            displayDogPic(it)

        })
    }

    fun removeObservers() {
        viewModel.getLoading().removeObservers(this)
        Log.d("MainActivity", "getLoading() observer removed")


        viewModel.getError().removeObservers(this)
        Log.d("MainActivity", "getERROR() observer removed")


        viewModel.getDog().removeObservers(this)
        Log.d("MainActivity", "getDog() observer removed")

    }

    // 1


}