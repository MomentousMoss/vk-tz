package com.momentousmoss.vktz.ui.scroll

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.momentousmoss.vktz.loaders.DEFAULT_SKIP
import com.momentousmoss.vktz.loaders.JsonObjectService
import com.momentousmoss.vktz.loaders.Products
import com.momentousmoss.vktz.ui.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScrollViewModel : MainViewModel() {

    val addNewProducts = MutableLiveData<List<JsonObjectService.Product?>>()

    fun init(productPage: Int) {
        requestProductsData(productPage)
    }

    fun requestProductsData(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            Products().requestProductsData(page)?.let {
                addNewProducts.postValue(it)
            }
        }
    }
}