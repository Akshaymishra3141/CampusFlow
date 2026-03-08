package com.example.smartcampusapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartcampusapp.AdminLoginActivity
import com.example.smartcampusapp.EventsActivity
import com.example.smartcampusapp.LostFoundActivity
import com.example.smartcampusapp.NoticeBoardActivity
import com.example.smartcampusapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardNotices.setOnClickListener {
            startActivity(Intent(this, NoticeBoardActivity::class.java))
        }
        binding.cardEvents.setOnClickListener {
            startActivity(Intent(this, EventsActivity::class.java))
        }
        binding.cardLostFound.setOnClickListener {
            startActivity(Intent(this, LostFoundActivity::class.java))
        }
        binding.btnAdminLogin.setOnClickListener {
            startActivity(Intent(this, AdminLoginActivity::class.java))
        }

    }
}