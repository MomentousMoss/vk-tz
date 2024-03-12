package com.momentousmoss.vktz.loaders

import android.net.Uri
import android.util.Xml
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.InputStreamReader
import java.net.URL

@Suppress("DEPRECATION")
class Products {

    fun requestProductsData(page: Int): List<JsonObjectService.Product?>? {
        val productsUrl = productsUrl(page)
        return try {
            val productsInputStream = productsUrl.openConnection().getInputStream()
            val productsJson = JsonParser().parse(
                InputStreamReader(productsInputStream, Xml.Encoding.UTF_8.name)
            ) as JsonObject
            Gson().fromJson(productsJson, JsonObjectService.Products::class.java).products
        } catch (e: Exception) {
            null
        }
    }

    private fun productsUrl(page: Int): URL {
        val scheme = "https"
        val authority = "dummyjson.com"
        val restPath = "products"
        val productsJson = getProductsJson(page)
        val urlBuilder = Uri.Builder()
            .scheme(scheme)
            .authority(authority)
            .appendPath(restPath)
            .appendQueryParameter(JsonObjectService.ProductsRequest::skip.name, productsJson.skip.toString())
            .appendQueryParameter(JsonObjectService.ProductsRequest::limit.name, productsJson.limit.toString())
        return URL(urlBuilder.build().toString())
    }

    private fun getProductsJson(page: Int): JsonObjectService.ProductsRequest {
        return JsonObjectService.ProductsRequest().apply {
            this.skip = page * DEFAULT_LIMIT
            this.limit = DEFAULT_LIMIT
        }
    }
}