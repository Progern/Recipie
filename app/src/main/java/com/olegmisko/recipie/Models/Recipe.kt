package com.olegmisko.recipie.Models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass


/*

   This class represents independent recipe
   from Hits JSON-Array

 */

@RealmClass
open class Recipe constructor(open var label: String,

                              open var image: String,

                              open var url: String,

                              open var ingredients: RealmList<Ingredient>?,

                              open var calories: Float,

                              open var totalWeight: Float,

                              open var id: Int,

                              open var isFavorite: Boolean) : RealmObject() {

    /* Default constructor for Realm */
    constructor() : this("Label", "Image_URL", "URL", null, 0F, 0F, -1, false )

}



