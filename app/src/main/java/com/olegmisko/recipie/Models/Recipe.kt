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

                              open var id: Int) : RealmObject() {

    /* Default constructor for Realm */
    constructor() : this("", "", "", -1)

}



