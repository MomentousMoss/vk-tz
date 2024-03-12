package com.momentousmoss.vktz.ui.product

import android.os.Parcelable
import com.momentousmoss.vktz.loaders.JsonObjectService
import kotlinx.parcelize.Parcelize

@Parcelize
class ProductArg internal constructor(
    var product: Product
) : Parcelable

@Parcelize
class Product (
    var id : Int? = null,
    var title : String? = null,
    var description : String? = null,
    var price : Float? = null,
    var discountPercentage : Float? = null,
    var rating : Float? = null,
    var stock : Int? = null,
    var brand : String? = null,
    var category : String? = null,
    var thumbnail : String? = null,
    var images : List<String>? = null
) : Parcelable