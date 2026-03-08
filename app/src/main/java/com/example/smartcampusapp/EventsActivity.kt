package com.example.smartcampusapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartcampusapp.adapters.EventAdapter
import com.example.smartcampusapp.databinding.ActivityEventsBinding
import com.example.smartcampusapp.repo.DummyRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.example.smartcampusapp.models.Event

class EventsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventsBinding
    private val list = mutableListOf<Event>()
    private lateinit var adapter: EventAdapter
    private val db by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = EventAdapter(list)
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        db.collection("events")
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                list.clear()
                snapshot?.forEach { doc ->
                    doc.toObject(Event::class.java)?.let { list.add(it) }
                }
                adapter.notifyDataSetChanged()
            }
    }
}