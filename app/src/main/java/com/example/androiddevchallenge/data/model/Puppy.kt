package com.example.androiddevchallenge.data.model

import java.io.Serializable

data class Puppy (
    var id: String? = null,
    val name: String? = null,
    val location: String? = null,
    val type: PuppyType? = null,
    val male: Boolean? = false,
    val age: String? = null,
    val description: String? = null,
    val tags: List<String> = arrayListOf(),
    val image: String? = null,
) : Serializable