package com.blogspot.soyamr.newsplusplus.presentation.image

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.blogspot.soyamr.newsplusplus.R
import com.blogspot.soyamr.newsplusplus.databinding.FragmentChooseImageBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ChooseImageFragment : Fragment(R.layout.fragment_choose_image) {

    private val binding: FragmentChooseImageBinding by viewBinding()
    lateinit var currentPhotoPath: String

    private var takeImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                launchNextFragmentWithImage(File(currentPhotoPath).toURI().toString())
            } else {
                showErrorToUser(TAKE_PICTURE_ERROR)
            }
        }

    private var pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    val imageUri: Uri = result.data?.data as Uri
                    val imageUriString = imageUri.toString()
                    launchNextFragmentWithImage(imageUriString)
                } catch (e: Exception) {
                    showErrorToUser(PICK_PICTURE_ERROR)
                }
            } else {
                showErrorToUser(PICK_PICTURE_ERROR)
            }
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chooseImageFromGalleryButtonView.setOnClickListener {
            dispatchPickPictureIntent()
        }
        binding.TakePhotoButtonView.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    private fun showErrorToUser(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG)
            .show()
    }


    private fun launchNextFragmentWithImage(imagePathUriString: String) {
        findNavController().navigate(
            ChooseImageFragmentDirections.actionChooseImageFragmentToShowImageFragment(
                imagePathUriString
            )
        )
    }

    private fun dispatchPickPictureIntent() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        pickImageLauncher.launch(photoPickerIntent)
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Toast.makeText(requireContext(), CAN_NOT_CREATE_PICTURE_ERROR, Toast.LENGTH_SHORT)
                        .show()
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.blogspot.soyamr.newsplusplus.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    takeImageLauncher.launch((takePictureIntent))
                }
            }
        }
    }

    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }
}