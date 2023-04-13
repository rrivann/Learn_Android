package com.dicoding.storyappsubmission.ui.addStory

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicoding.storyappsubmission.databinding.FragmentAddstoryBinding
import com.dicoding.storyappsubmission.ui.camera.CameraActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dicoding.storyappsubmission.R
import com.dicoding.storyappsubmission.utils.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.FileOutputStream
import java.io.OutputStream

@AndroidEntryPoint
class AddStoryFragment : Fragment() {

    private var _binding: FragmentAddstoryBinding? = null
    private val binding get() = _binding!!

    private val addStoryViewModel: AddStoryViewModel by viewModels()
    private var getFile: File? = null

    private var token: String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddstoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            addStoryViewModel.getAuthToken().collect { authToken ->
                if (!authToken.isNullOrEmpty()) token = authToken
            }
        }

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.btnCamera.setOnClickListener { startCameraX() }
        binding.btnGallery.setOnClickListener { startGallery() }
        binding.btnUpload.setOnClickListener { addStory() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun startCameraX() {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun addStory() {
        if (getFile != null) {

            val description =
                binding.etDescription.text.toString().toRequestBody("text/plain".toMediaType())
            val descriptionBlank = binding.etDescription.text.toString().isBlank()

            val file = reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )
            if (descriptionBlank) binding.etDescription.error =
                getString(R.string.desc_empty_field_error) else {
                addStoryViewModel.addStory(token, imageMultipart, description, null, null)
                    .observe(requireActivity()) { result ->
                        if (result != null) when (result) {
                            is Result.Loading -> {
                                showLoading(true, binding.progressBar)
                            }
                            is Result.Success -> {
                                showLoading(false, binding.progressBar)
                                Toast.makeText(
                                    requireContext(),
                                    R.string.story_upload,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                getFile = null
                                binding.etDescription.text.clear()
                                val navController = findNavController()
                                navController.navigate(R.id.navigation_home)

                            }
                            is Result.Error -> {
                                showLoading(false, binding.progressBar)
                                Toast.makeText(
                                    requireContext(),
                                    R.string.story_upload_failed,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    }
            }
        } else {
            Toast.makeText(
                requireContext(),
                R.string.empty_image_error,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val os: OutputStream
            val myFile =
                it.data?.getSerializableExtra("picture")
                        as File
             val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )

            try {
                os = FileOutputStream(myFile)
                result.compress(Bitmap.CompressFormat.JPEG, 100, os)
                os.flush()
                os.close()

                getFile = myFile
            } catch (e: Exception) {
                e.printStackTrace()
            }

//            getFile = myFile

            binding.previewImageView.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireActivity())
            getFile = myFile
            binding.previewImageView.setImageURI(selectedImg)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        @Suppress("DEPRECATION")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    requireContext(),
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                activity?.finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            activity?.baseContext!!,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

}