package com.example.smartcampusapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartcampusapp.databinding.ActivityPostEventBinding
import com.example.smartcampusapp.models.Event
import com.example.smartcampusapp.repo.DummyRepository
import com.google.firebase.firestore.FirebaseFirestore

class PostEventActivity : AppCompatActivity() {

    // ViewBinding object
    private lateinit var binding: ActivityPostEventBinding
    private val db by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Button click listener for posting an event
        binding.btnPost.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val desc = binding.etDesc.text.toString().trim()
            val date = binding.etDate.text.toString().trim()

            // Simple validation
            if (title.isEmpty() || desc.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val event = Event(title, desc, date);
            binding.btnPost.isEnabled = false

            db.collection("events")
                .add(event)
                .addOnSuccessListener {
                    Toast.makeText(this, "Event posted", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    binding.btnPost.isEnabled = true
                    Toast.makeText(this, "Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}