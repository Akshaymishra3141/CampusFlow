package com.example.smartcampusapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smartcampusapp.databinding.ActivityAdminDashboardBinding

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Post Notice button
        binding.btnPostNotice.setOnClickListener {
            startActivity(Intent(this, PostNoticeActivity::class.java))
        }

        // Post Event button
        binding.btnPostEvent.setOnClickListener {
            startActivity(Intent(this, PostEventActivity::class.java))
        }
    }
}
