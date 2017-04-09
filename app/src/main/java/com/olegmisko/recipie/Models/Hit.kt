package com.olegmisko.recipie.Models

/*
    This class represents the object that stores
    the @recipe itself, @bookmarked and @bought
    parameters.
    We achieve a list of these objects by response.
 */

class Hit(val recipe: Recipe,

          val bookmarked: Boolean,

          val bought: Boolean)
