package com.example.smartcampusapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartcampusapp.databinding.ActivityAdminLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult
class AdminLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminLoginBinding
    private val auth by lazy { FirebaseAuth.getInstance() }

    private val adminEmail = "admin@smartcampus.edu"
    private val adminPassword = "admin1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val pass = binding.etPassword.text.toString().trim()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(email,pass).addOnSuccessListener {
                Toast.makeText(this, "Admin login successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, AdminDashboardActivity::class.java))
                finish()
            }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Login failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }

        }
    }
}