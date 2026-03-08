package com.example.smartcampusapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartcampusapp.adapters.LostFoundAdapter
import com.example.smartcampusapp.databinding.ActivityLostFoundBinding
import com.example.smartcampusapp.models.LostFoundItem
import com.google.firebase.firestore.FirebaseFirestore

class LostFoundActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLostFoundBinding
    private val list = mutableListOf<LostFoundItem>()
    private lateinit var adapter: LostFoundAdapter
    private val db by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLostFoundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = LostFoundAdapter(list)
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        db.collection("lost_found")
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                list.clear()
                snapshot?.forEach { doc ->
                    doc.toObject(LostFoundItem::class.java)?.let { list.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, PostLostFoundActivity::class.java))
        }
    }
}