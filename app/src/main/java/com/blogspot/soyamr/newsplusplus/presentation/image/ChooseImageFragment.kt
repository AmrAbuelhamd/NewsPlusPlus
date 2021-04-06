package com.blogspot.soyamr.newsplusplus.presentation.image

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.blogspot.soyamr.newsplusplus.BuildConfig
import com.blogspot.soyamr.newsplusplus.R
import com.blogspot.soyamr.newsplusplus.databinding.FragmentChooseImageBinding
import com.blogspot.soyamr.newsplusplus.presentation.image.util.ImageUtils


class ChooseImageFragment : Fragment(R.layout.fragment_choose_image) {

    private val binding: FragmentChooseImageBinding by viewBinding()
    private var imageUri: Uri? = null

    private var takeImageLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { result ->
            if (result && imageUri != null) {
                launchNextFragmentWithImage(imageUri!!)
            } else {
                showErrorToUser(TAKE_PICTURE_ERROR)
            }
        }

    private var pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                launchNextFragmentWithImage(uri)
            } else {
                showErrorToUser(PICK_PICTURE_ERROR)
            }
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chooseImageFromGalleryButtonView.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }
        binding.TakePhotoButtonView.setOnClickListener {
            takePicture.run()
        }
    }

    private val takePicture: Runnable = Runnable {
        ImageUtils.createImageFile(requireContext())?.also {
            imageUri = FileProvider.getUriForFile(
                requireContext(),
                BuildConfig.APPLICATION_ID + ".fileprovider",
                it
            )
            takeImageLauncher.launch(imageUri)
        }
    }

    private fun showErrorToUser(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT)
            .show()
    }

    private fun launchNextFragmentWithImage(imagePathUriString: Uri) {
        findNavController().navigate(
            ChooseImageFragmentDirections.actionChooseImageFragmentToShowImageFragment(
                imagePathUriString.toString()
            )
        )
    }
}