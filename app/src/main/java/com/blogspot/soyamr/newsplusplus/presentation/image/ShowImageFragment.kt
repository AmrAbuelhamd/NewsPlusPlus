package com.blogspot.soyamr.newsplusplus.presentation.image

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.blogspot.soyamr.newsplusplus.R
import com.blogspot.soyamr.newsplusplus.databinding.FragmentShowImageBinding
import com.blogspot.soyamr.newsplusplus.presentation.image.util.ImageUtils
import com.blogspot.soyamr.newsplusplus.presentation.image.util.ZoomManger

class ShowImageFragment : Fragment(R.layout.fragment_show_image) {

    private val binding: FragmentShowImageBinding by viewBinding()

    private val args: ShowImageFragmentArgs by navArgs()


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageUriString = args.imageUri
        val imageUri = Uri.parse(imageUriString)

        try {
            val resultImage = ImageUtils.handleSamplingAndRotationBitmap(requireContext(), imageUri)
            binding.imageView2.setImageBitmap(resultImage)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), SOMETHING_WENT_WRONG, Toast.LENGTH_LONG).show()
            findNavController().popBackStack()
        }
        val zoom = ZoomManger()
        binding.imageView2.setOnTouchListener { v, event ->
            val imageView: ImageView = v as ImageView
            imageView.bringToFront()
            zoom.viewTransformation(imageView, event)
            true
        }
        binding.toolbar2.setupWithNavController(findNavController())

    }


    override fun onResume() {
        super.onResume()
        binding.toolbar2.title = "Your Image"
    }
}