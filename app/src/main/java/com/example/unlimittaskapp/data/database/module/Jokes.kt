package com.example.unlimittaskapp.data.database.module

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.Date

open class Jokes (

    @field:SerializedName("joke")
    var jokes: String? = null,

    @PrimaryKey
    var jokesId: String? = null,


    var startDateStr: String? = null,

    var startDate: Date? = null
):  RealmObject()