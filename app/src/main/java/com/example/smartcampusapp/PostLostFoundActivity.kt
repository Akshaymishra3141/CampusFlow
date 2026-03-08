package com.example.smartcampusapp

import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.smartcampusapp.databinding.ActivityPostLostfoundBinding
import com.example.smartcampusapp.models.LostFoundItem
import com.google.firebase.firestore.FirebaseFirestore
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import kotlin.concurrent.thread

class PostLostFoundActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostLostfoundBinding
    private var selectedImageUri: Uri? = null
    private val db by lazy { FirebaseFirestore.getInstance() }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            selectedImageUri = it
            binding.ivPreview.setImageURI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostLostfoundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOf("Lost", "Found"))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerType.adapter = adapter

        binding.btnPickImage.setOnClickListener { pickImage.launch("image/*") }

        binding.btnPost.setOnClickListener { postItem() }
    }

    private fun postItem() {
        val type = binding.spinnerType.selectedItem.toString()
        val desc = binding.etDesc.text.toString().trim()
        val contact = binding.etContact.text.toString().trim()

        if (desc.isEmpty() || contact.isEmpty()) {
            Toast.makeText(this, "Please fill description and contact", Toast.LENGTH_SHORT).show()
            return
        }

        binding.btnPost.isEnabled = false

        if (selectedImageUri != null) {
            uploadToCloudinary(selectedImageUri!!) { imageUrl ->
                saveToFirestore(type, desc, contact, imageUrl)
            }
        } else {
            saveToFirestore(type, desc, contact, "")
        }
    }

    private fun uploadToCloudinary(uri: Uri, onUploaded: (String) -> Unit) {
        Toast.makeText(this, "Uploading image...", Toast.LENGTH_SHORT).show()

        val cloudName = "dcy1finba"
        val apiKey = "625365914325785"
        val uploadPreset = "ml_default"

        val uploadUrl = "https://api.cloudinary.com/v1_1/$cloudName/image/upload"

        thread {
            try {
                val inputStream = contentResolver.openInputStream(uri)
                val bytes = inputStream!!.readBytes()

                val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                        "file",
                        "image.jpg",
                        bytes.toRequestBody("image/*".toMediaTypeOrNull())
                    )
                    .addFormDataPart("upload_preset", uploadPreset)
                    .addFormDataPart("api_key", apiKey)
                    .build()

                val request = Request.Builder()
                    .url(uploadUrl)
                    .post(requestBody)
                    .build()

                val client = OkHttpClient()
                val response = client.newCall(request).execute()
                val json = JSONObject(response.body!!.string())
                val imageUrl = json.getString("secure_url")

                runOnUiThread {
                    onUploaded(imageUrl)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    binding.btnPost.isEnabled = true
                    Toast.makeText(this, "Upload failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun saveToFirestore(type: String, desc: String, contact: String, imgUrl: String) {
        val item = LostFoundItem(type, desc, contact, imgUrl)

        db.collection("lost_found")
            .add(item)
            .addOnSuccessListener {
                Toast.makeText(this, "Posted Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                binding.btnPost.isEnabled = true
                Toast.makeText(this, "Failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}