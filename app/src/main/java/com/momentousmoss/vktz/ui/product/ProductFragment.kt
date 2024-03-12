package com.momentousmoss.vktz.ui.product

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.momentousmoss.vktz.R
import com.momentousmoss.vktz.databinding.FragmentProductBinding
import com.momentousmoss.vktz.ui.main.MainFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductFragment : MainFragment() {

    private val productViewModel by viewModel<ProductViewModel>()
    private var productArg: ProductArg? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ProductFragmentArgs.fromBundle(requireArguments()).let {
                productArg = it.product
                productViewModel.init(
                    productArg = productArg,
                    resources
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding: FragmentProductBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_product,
            container,
            false
        )
        binding.apply {
            viewModel = productViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        productViewModel.apply {
            setThumbnail.observe(viewLifecycleOwner) {
                binding.apply {
                    loadThumbnail(it, productImage, imageProgressLoader, requireContext())
                }
            }
            setImages.observe(viewLifecycleOwner) {
                binding.imagesList.apply {
                    adapter = ImagesAdapter(
                        context,
                        it
                    )
                }
            }
        }
        return binding.root
    }

    private fun loadThumbnail(
        thumbnail: String?, productImage: ImageView, imageProgressLoader: ProgressBar, context: Context
    ) {
        Glide.with(context)
            .load(thumbnail)
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