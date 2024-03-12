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
    private val utf8 = Xml.Encoding.UTF_8.name

    fun requestProductsData(page: Int): List<JsonObjectService.Product?>? {
        val searchUrl = getSearchUrl(page)
        return try {
            val searchInputStream = searchUrl.openConnection().getInputStream()
            val searchJson = JsonParser().parse(InputStreamReader(searchInputStream, utf8)) as JsonObject
            Gson().fromJson(searchJson, JsonObjectService.Products::class.java).products
        } catch (e: Exception) {
            null
        }
    }

    private fun getSearchUrl(page: Int): URL {
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
            this.skip = page * DEFAULT_SKIP
            this.limit = DEFAULT_LIMIT
        }
    }
}