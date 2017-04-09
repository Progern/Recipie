package com.olegmisko.recipie.Models


/*

   This class represents independent recipe
   from Hits JSON-Array

 */
class Recipe(val label: String,

             val image: String,

             val url : String,

             val ingredientLines : ArrayList<String>)