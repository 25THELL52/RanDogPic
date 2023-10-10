package com.example.randogpic.data.repository

import com.example.randogpic.model.Dog


interface RepositoryCallback  {
     fun onSuccess(dog : Dog) {
// TODO
    }
     fun onError(e: String) {
// TODO
    }
}
