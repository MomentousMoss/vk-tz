package com.momentousmoss.vktz.ui.product

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.momentousmoss.vktz.R
import com.momentousmoss.vktz.databinding.ItemImageProductBinding
import com.momentousmoss.vktz.utils.toProductArg

class ImagesAdapter(
    private val context: Context,
    private val imagesList: List<String>?
) : RecyclerView.Adapter<ImagesAdapter.ProductViewHolder>() {

    class ProductViewHolder internal constructor(
        binding: ItemImageProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val productImage: ImageView = binding.productImage
        val imageProgressLoader: ProgressBar = binding.imageProgressLoader
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ProductViewHolder {
        return ProductViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.item_image_product,
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(productViewHolder: ProductViewHolder, i: Int) {
        imagesList?.get(i)?.apply {
            val image = this
            productViewHolder.apply {
                if (image.isEmpty()) {
                    productImage.visibility = View.GONE
                    imageProgressLoader.visibility = View.GONE
                } else {
                    Glide.with(context)
                        .load(image)
                        .listener(object : RequestListener<Drawable> {
                            override fun onResourceReady(
                                resource: Drawable,
                                model: Any,
                                target: Target<Drawable>?,
                                dataSource: DataSource,
                                isFirstResource: Boolean
                            ): Boolean {
                                imageProgressLoader.visibility = View.GONE
                                return false
                            }

                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>,
                                isFirstResource: Boolean
                            ): Boolean {
                                imageProgressLoader.visibility = View.GONE
                                productImage.visibility = View.GONE
                                return false
                            }
                        })
                        .into(productImage)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return imagesList?.size ?: 0
    }
}