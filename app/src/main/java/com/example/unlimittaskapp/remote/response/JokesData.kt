package com.example.unlimittaskapp.remote.response

import com.google.gson.annotations.SerializedName
import io.realm.RealmList

data class JokesData (
    @field:SerializedName("joke")
    var joke: String?= null
)