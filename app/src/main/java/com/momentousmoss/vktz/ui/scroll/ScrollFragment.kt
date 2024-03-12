package com.momentousmoss.vktz.ui.scroll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.momentousmoss.vktz.R
import com.momentousmoss.vktz.databinding.FragmentScrollBinding
import com.momentousmoss.vktz.loaders.JsonObjectService
import com.momentousmoss.vktz.ui.main.MainFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScrollFragment : MainFragment() {

    private val scrollViewModel by viewModel<ScrollViewModel>()

    private val productsList: MutableList<JsonObjectService.Product?> = mutableListOf()

    override fun init() {
        scrollViewModel.init()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding: FragmentScrollBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_scroll,
            container,
            false
        )
        binding.apply {
            viewModel = scrollViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        scrollViewModel.apply {
            addNewProducts.observe(viewLifecycleOwner) {
                productsList.addAll(it)
                binding.productsScrollList.apply {
                    adapter = ProductsAdapter(
                        context,
                        productsList
                    )
                }
            }
        }
        return binding.root
    }

}