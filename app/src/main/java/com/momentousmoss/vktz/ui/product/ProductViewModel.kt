package com.momentousmoss.vktz.ui.product

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.momentousmoss.vktz.R
import com.momentousmoss.vktz.loaders.JsonObjectService
import com.momentousmoss.vktz.ui.main.MainViewModel

class ProductViewModel : MainViewModel() {

    private var productArg: ProductArg? = null

    val setThumbnail = MutableLiveData<String?>()

    val productTitle = MutableLiveData<String>()
    val productDescription = MutableLiveData<String>()
    val productPrice = MutableLiveData<String>()
    val productRating = MutableLiveData<String>()

    fun init(productArg: ProductArg?, context: Context) {
        this.productArg = productArg
        productArg?.product?.apply {
            productTitle.value = title ?: ""
            productDescription.value = description ?: ""
            productPrice.value = context.resources.getString(R.string.product_price, if (price != null) price.toString() else "?")
            productRating.value = context.resources.getString(R.string.product_rating, if (rating != null) rating.toString() else "?")

            setThumbnail.postValue(thumbnail)
        }
    }

}