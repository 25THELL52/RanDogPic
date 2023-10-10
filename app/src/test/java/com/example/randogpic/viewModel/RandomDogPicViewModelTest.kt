package com.example.randogpic.viewModel

import android.util.AndroidException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.randogpic.data.repository.RandomDogPicRepository
import com.example.randogpic.data.repository.RandomDogPicRepositoryImpl
import com.example.randogpic.data.repository.RepositoryCallback
import com.example.randogpic.model.Dog
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.times
import org.mockito.kotlin.whenever
import org.mockito.stubbing.Answer


@RunWith(MockitoJUnitRunner::class)


class RandomDogPicViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var randomDogPicRepository: RandomDogPicRepository
    lateinit var randomDogPicViewModel: RandomDogPicViewModel
    @Mock
    lateinit var dog: Dog

    @Mock
    lateinit var loadingObserver: Observer<Boolean>
    @Mock
    lateinit var errorObserver: Observer<Boolean>
    @Mock
    lateinit var dogObserver: Observer<Dog>


    @Before
    fun setUp() {

        randomDogPicViewModel = RandomDogPicViewModel(randomDogPicRepository)
        randomDogPicViewModel.let {
            it.getDog().observeForever(dogObserver)
            it.getLoading().observeForever(loadingObserver)
            it.getError().observeForever(errorObserver)

        }


    }
    private fun setUpRepositoryWithSuccess() {

        doAnswer {
            val repositoryCallback = it.getArgument<RepositoryCallback>(0)
            repositoryCallback.onSuccess(dog)
        }.whenever(randomDogPicRepository).getDog(any())

    }

    private fun setUpRepositoryWithFailure() {

        doAnswer {
            val repositoryCallback = it.getArgument<RepositoryCallback>(0)
            repositoryCallback.onError("Couldn't get dog object")
        }.whenever(randomDogPicRepository).getDog(any())

    }


    @Test
    fun whenInitialize_fetchIsCalled() {

        val spyViewModel = spy(randomDogPicViewModel)
        spyViewModel.initialize()
        verify(spyViewModel).fetch()

    }

    @Test
    fun whenFetch_loadingIsTrue() {
        randomDogPicViewModel.fetch()
        verify(loadingObserver).onChanged(true)
    }

    @Test
    fun whenFetch_ErrorIsFalse() {
        randomDogPicViewModel.fetch()
        verify(errorObserver).onChanged(false)
    }

    @Test
    fun whenFetch_getDogIsCalled() {
        randomDogPicViewModel.fetch()
        verify(randomDogPicRepository).getDog(any())
    }


    @Test
    fun whenRepositoryReturnsSuccess_loadingIsFalse() {
        setUpRepositoryWithSuccess()
        randomDogPicViewModel.fetch()
        verify(loadingObserver).onChanged(false)
    }

    @Test
    fun whenRepositoryReturnsSuccess_errorIsFalse() {
        setUpRepositoryWithSuccess()
        randomDogPicViewModel.fetch()
        verify(errorObserver,times(2)).onChanged(false)
    }

    @Test
    fun whenRepositoryReturnsSuccess_DogValueIsChangedTodog() {
        setUpRepositoryWithSuccess()
        randomDogPicViewModel.fetch()
        verify(dogObserver).onChanged(dog)
    }

    @Test
    fun whenRepositoryReturnsError_loadingIsFalse() {
        setUpRepositoryWithFailure()
        randomDogPicViewModel.fetch()
        verify(loadingObserver).onChanged(false)
    }

    @Test
    fun whenRepositoryReturnsError_errorIsTrue() {
        setUpRepositoryWithFailure()
        randomDogPicViewModel.fetch()
        verify(errorObserver).onChanged(true)
    }



}