package com.example.smartcampusapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartcampusapp.databinding.ActivityPostNoticeBinding
import com.example.smartcampusapp.models.Notice
import com.example.smartcampusapp.repo.DummyRepository
import com.google.firebase.firestore.FirebaseFirestore


class PostNoticeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostNoticeBinding
    private val db by lazy { FirebaseFirestore.getInstance() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPost.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val desc = binding.etDesc.text.toString().trim()
            val date = binding.etDate.text.toString().trim()
            if (title.isEmpty() || desc.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.btnPost.isEnabled = false
            val notice = Notice(title, desc, date)
            db.collection("notices")
                .add(notice)
                .addOnSuccessListener {
                    Toast.makeText(this, "Notice posted", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    binding.btnPost.isEnabled = true
                    Toast.makeText(this, "Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}