package com.blogspot.soyamr.newsplusplus.presentation.news_list.webview

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.blogspot.soyamr.newsplusplus.R
import com.blogspot.soyamr.newsplusplus.databinding.FragmentWebViewBinding


class WebViewFragment : Fragment(R.layout.fragment_web_view) {

    private val binding: FragmentWebViewBinding by viewBinding()
    private val args: WebViewFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webView.loadUrl(args.webUrl)
        binding.progressBar.visibility = View.VISIBLE

        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageCommitVisible(view, url)
                binding.progressBar.visibility = View.INVISIBLE
            }
        }

        binding.toolbar.setupWithNavController(findNavController())

    }

    override fun onResume() {
        super.onResume()
        binding.toolbar.title = "News Website"
    }
}