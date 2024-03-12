package com.momentousmoss.vktz.ui.scroll

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.momentousmoss.vktz.R
import com.momentousmoss.vktz.databinding.ItemScrollBinding
import com.momentousmoss.vktz.loaders.JsonObjectService

class ProductsAdapter(
    private val context: Context,
    private val productsList: MutableList<JsonObjectService.Product?>
) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    class ProductViewHolder internal constructor(binding: ItemScrollBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val productTitle: TextView = binding.productTitle
        val productDescription: TextView = binding.productDescription
        val productImage: ImageView = binding.productImage
        val imageProgressLoader: ProgressBar = binding.imageProgressLoader
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
                if (thumbnail.isNullOrEmpty()) {
                    productImage.visibility = View.GONE
                } else {
                    Glide.with(context)
                        .load(thumbnail)
                        .listener(object : RequestListener<Drawable> {
                            override fun onResourceReady(
                                resource: Drawable,
                                model: Any,
                                target: com.bumptech.glide.request.target.Target<Drawable>?,
                                dataSource: DataSource,
                                isFirstResource: Boolean
                            ): Boolean {
                                imageProgressLoader.visibility = View.INVISIBLE
                                return false
                            }

                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>,
                                isFirstResource: Boolean
                            ): Boolean {
                                imageProgressLoader.visibility = View.INVISIBLE
                                return false
                            }
                        })
                        .into(productImage)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return productsList.size
    }
}