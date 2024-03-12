package com.momentousmoss.vktz.utils

import com.momentousmoss.vktz.loaders.JsonObjectService
import com.momentousmoss.vktz.ui.product.Product
import com.momentousmoss.vktz.ui.product.ProductArg

fun JsonObjectService.Product.toProductArg() = ProductArg(
    product = Product(
        id = this.id,
        title = this.title,
        description = this.description,
        price = this.price,
        discountPercentage = this.discountPercentage,
        rating = this.rating,
        stock = this.stock,
        brand = this.brand,
        category = this.category,
        thumbnail = this.thumbnail,
        images = this.images
    )
)