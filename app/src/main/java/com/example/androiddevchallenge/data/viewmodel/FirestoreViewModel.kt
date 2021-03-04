package com.example.androiddevchallenge.data.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androiddevchallenge.data.model.Puppy
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

class FirestoreViewModel(app: Application) : AndroidViewModel(app), CoroutineScope {
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private val COLLECTION = "challengeweek1"

    private var _puppyItems = MutableLiveData(listOf<Puppy>())
    val puppyItems: LiveData<List<Puppy>> = _puppyItems

    fun getPuppies(){
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection(COLLECTION)
        val source = Source.DEFAULT
        val itemList = ArrayList<Puppy>()
        docRef.get(source).addOnSuccessListener { result ->
            for (document in result) {
                val puppy = document.toObject(Puppy::class.java)
                puppy.id = document.id
                itemList.add(puppy)
            }
            _puppyItems.postValue(itemList)
        }
    }
}