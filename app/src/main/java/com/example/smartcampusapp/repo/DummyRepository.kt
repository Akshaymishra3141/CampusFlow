package com.example.smartcampusapp.repo

import com.example.smartcampusapp.models.Notice
import com.example.smartcampusapp.models.Event
import com.example.smartcampusapp.models.LostFoundItem

object DummyRepository {

    val notices = mutableListOf<Notice>()
    val events = mutableListOf<Event>()
    val lostFoundItems = mutableListOf<LostFoundItem>()

    fun addNotice(title: String, description: String, date: String): Notice {
        val notice = Notice(title, description, date)
        notices.add(0, notice)
        return notice
    }

    fun addEvent(title: String, description: String, date: String): Event {
        val event = Event(title, description, date)
        events.add(0, event)
        return event
    }

    fun addLostFound(type: String, description: String, contact: String, imageUriString: String): LostFoundItem {
        val item = LostFoundItem(type, description, contact, imageUriString)
        lostFoundItems.add(0, item)
        return item
    }
}