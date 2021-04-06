package com.blogspot.soyamr.newsplusplus.presentation.image

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.blogspot.soyamr.newsplusplus.R
import com.blogspot.soyamr.newsplusplus.databinding.FragmentShowImageBinding
import java.io.InputStream

class ShowImageFragment : Fragment(R.layout.fragment_show_image) {

    private val binding: FragmentShowImageBinding by viewBinding()

    private val args: ShowImageFragmentArgs by navArgs()


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageUriString = args.imageUri
        val imageUri = Uri.parse(imageUriString)

        try {
            val imageStream: InputStream? =
                requireActivity().contentResolver.openInputStream(imageUri)
            val selectedImage = BitmapFactory.decodeStream(imageStream)
            binding.imageView2.setImageBitmap(selectedImage)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), SOMETHING_WENT_WRONG, Toast.LENGTH_LONG).show()
            findNavController().popBackStack()
        }
        val zoom = ZoomView()
        binding.imageView2.setOnTouchListener { v, event ->
            val imageView: ImageView = v as ImageView
            imageView.bringToFront()
            zoom.viewTransformation(imageView, event)
            true
        }

    }
}