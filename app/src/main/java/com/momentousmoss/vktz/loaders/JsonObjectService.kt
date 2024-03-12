package com.momentousmoss.vktz.loaders

const val DEFAULT_LIMIT = 20
const val DEFAULT_SKIP = 0

open class JsonObjectService {
    data class ProductsRequest (
        var skip : Int = DEFAULT_SKIP,
        var limit : Int = DEFAULT_LIMIT
    )

    data class Product (
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
    )

    data class Products (
        var products: List<Product?>? = null
    )
}