package com.momentousmoss.vktz.ui.scroll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.momentousmoss.vktz.R
import com.momentousmoss.vktz.databinding.FragmentScrollBinding
import com.momentousmoss.vktz.loaders.DEFAULT_SKIP
import com.momentousmoss.vktz.loaders.JsonObjectService
import com.momentousmoss.vktz.ui.main.MainFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScrollFragment : MainFragment() {

    private val scrollViewModel by viewModel<ScrollViewModel>()
    private var productPage = DEFAULT_SKIP
    private val productsList: MutableList<JsonObjectService.Product?> = mutableListOf()
    private var productsAdapter : ProductsAdapter? = null

    override fun init() {
        scrollViewModel.init(productPage)
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
            bottomNavigation.setupWithNavController(findNavController())

            productsScrollList.apply {
                productsAdapter = ProductsAdapter(
                    context,
                    scrollViewModel.navigateToProductFragment,
                    productsList
                )
                adapter = productsAdapter
            }
            productsScrollList.addOnScrollListener(scrollListener)
        }
        scrollViewModel.apply {
            addNewProducts.observe(viewLifecycleOwner) {
                if (productsList.contains(it[0])) return@observe
                productsList.addAll(it)
                productsAdapter?.apply {
                    this.notifyItemRangeInserted(this.itemCount, it.size)
                }
            }
            navigateToProductFragment.observe(viewLifecycleOwner) {
                findNavController().navigate(
                    ScrollFragmentDirections.actionScrollFragmentToProductFragment(
                        it
                    )
                )
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
        return binding.root
    }

    private var scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1)
                && newState == RecyclerView.SCROLL_STATE_IDLE
            ) {
                productPage++
                scrollViewModel.requestProductsData(productPage)
            }
        }
    }

}