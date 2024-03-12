package com.momentousmoss.vktz.ui.product

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import com.momentousmoss.vktz.R
import com.momentousmoss.vktz.ui.main.MainViewModel

class ProductViewModel : MainViewModel() {

    private var productArg: ProductArg? = null

    val setThumbnail = MutableLiveData<String?>()
    val setImages = MutableLiveData<List<String>?>()

    val productTitle = MutableLiveData<String>()
    val productDescription = MutableLiveData<String>()
    val productPrice = MutableLiveData<String>()
    val productRating = MutableLiveData<String>()

    fun init(productArg: ProductArg?, resources: Resources) {
        this.productArg = productArg
        productArg?.product?.apply {
            productTitle.value = title ?: ""
            productDescription.value = description ?: ""
            productPrice.value = resources.getString(R.string.product_price, if (price != null) price.toString() else "?")
            productRating.value = resources.getString(R.string.product_rating, if (rating != null) rating.toString() else "?")

            setThumbnail.postValue(thumbnail)
            setImages.postValue(images)
        }
    }

}