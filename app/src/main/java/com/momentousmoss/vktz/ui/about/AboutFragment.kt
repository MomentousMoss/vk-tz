package com.momentousmoss.vktz.ui.about

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.momentousmoss.vktz.R
import com.momentousmoss.vktz.databinding.FragmentAboutBinding
import com.momentousmoss.vktz.databinding.ItemAboutCardBinding
import com.momentousmoss.vktz.ui.main.MainFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import net.glxn.qrgen.android.QRCode

class AboutFragment : MainFragment() {

    private val aboutViewModel by viewModel<AboutViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding: FragmentAboutBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_about,
            container,
            false
        )
        binding.apply {
            viewModel = aboutViewModel
            lifecycleOwner = viewLifecycleOwner
            bottomNavigation.setupWithNavController(findNavController())

            setItemAboutCard(itemAboutCard)
            setQRCode(itemAboutCard)
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
        return binding.root
    }

    private fun setItemAboutCard(itemAboutCard: ItemAboutCardBinding) {
        itemAboutCard.apply {
            link.setOnClickListener {
                copyLinkToClipboard(requireContext(), link.text)
            }
            shareButton.setOnClickListener {
                shareLink(requireContext(), link.text)
            }
        }
    }

    private fun setQRCode(
        itemAboutCard: ItemAboutCardBinding
    ) {
        itemAboutCard.apply {
            qrCode.apply {
                setImageBitmap(qrGenerate(getString(R.string.about_url)))
            }
        }
    }

    private fun copyLinkToClipboard(context: Context, downloadLink: CharSequence) {
        val clipData = ClipData.newPlainText("downloadLink", downloadLink)
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(
            requireContext(), getString(R.string.about_toast_copied), Toast.LENGTH_LONG
        ).show()
    }

    private fun shareLink(context: Context, downloadLink: CharSequence) {
        val shareIntent = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, downloadLink)
        }, null)
        context.startActivity(shareIntent)
    }

    private fun qrGenerate(downloadLink: String): Bitmap {
        return QRCode.from(downloadLink).bitmap()
    }

}