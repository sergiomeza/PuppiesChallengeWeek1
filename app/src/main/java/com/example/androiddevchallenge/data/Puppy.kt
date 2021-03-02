package com.example.androiddevchallenge.data

import android.os.Parcelable
import java.io.Serializable

data class Puppy (
    val id: Int,
    val name: String,
    val location: String,
    val type: PuppyType? = null,
    val male: Boolean,
    val age: String,
    val description: String,
    val tags: List<PuppyTags> = arrayListOf(),
    val image: String,
) : Serializable