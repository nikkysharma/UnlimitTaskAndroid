package com.example.unlimittaskapp.data.database.module

import io.realm.annotations.RealmModule

@RealmModule(
    library = true,
    classes = [Jokes::class]
)
class UnLimitDBModule {
}