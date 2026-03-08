package com.example.smartcampusapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartcampusapp.adapters.NoticeAdapter
import com.example.smartcampusapp.databinding.ActivityNoticeBoardBinding
import com.example.smartcampusapp.models.Notice
import com.google.firebase.firestore.FirebaseFirestore

class NoticeBoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoticeBoardBinding
    private val list = mutableListOf<Notice>()
    private lateinit var adapter: NoticeAdapter
    private val db by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NoticeAdapter(list)
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        db.collection("notices")
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                list.clear()
                snapshot?.forEach { doc ->
                    doc.toObject(Notice::class.java)?.let { list.add(it) }
                }
                adapter.notifyDataSetChanged()
            }
    }
}