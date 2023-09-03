package com.example.unlimittaskapp.data.database.module

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.Date

open class Jokes (
    var jokes: String? = null,

    @PrimaryKey
    var jokesId: String? = null,


    var startDateStr: String? = null
):  RealmObject()