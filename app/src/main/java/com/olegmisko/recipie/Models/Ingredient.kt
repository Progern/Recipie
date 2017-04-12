package com.olegmisko.recipie.Models

import io.realm.RealmObject
import io.realm.annotations.RealmClass


@RealmClass
open class Ingredient(open var text: String,

                      open var weight: Float) : RealmObject() {

    constructor() : this("", 0F)
}
