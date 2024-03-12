package com.momentousmoss.vktz.ui.scroll

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.momentousmoss.vktz.R
import com.momentousmoss.vktz.databinding.ItemScrollBinding
import com.momentousmoss.vktz.loaders.JsonObjectService

class ProductsAdapter(
    context: Context,
    private val productsList: MutableList<JsonObjectService.Product?>
) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    class ProductViewHolder internal constructor(binding: ItemScrollBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val productTitle: TextView = binding.productTitle
        val productDescription: TextView = binding.productDescription
        val productImage: ImageView = binding.productImage
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ProductViewHolder {
        return ProductViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.item_scroll,
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(productViewHolder: ProductViewHolder, i: Int) {
        productsList[i]?.apply {
            productViewHolder.apply {
                productTitle.text = title
                productDescription.text = description
                productImage.setImageDrawable(null)
            }
        }
    }

    override fun getItemCount(): Int {
        return productsList.size
    }
}