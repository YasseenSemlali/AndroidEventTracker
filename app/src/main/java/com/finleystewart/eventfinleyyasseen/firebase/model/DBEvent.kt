package com.finleystewart.eventfinleyyasseen.firebase.model

import com.finleystewart.eventfinleyyasseen.business.Event
import com.finleystewart.eventfinleyyasseen.business.EventRepeat
import java.util.*

data class DBEvent (val category: String = "",
                    val shortDesc: String = "",
                    val longDesc: String = "",
                    val eventDate: Long = Calendar.getInstance().time.time,
                    val eventDuration: Double = 1.0,
                    val repeatType: EventRepeat = EventRepeat.SINGLE,
                    val timesRepeated: Int = 1,
                    val expired: Boolean = false,
                    val siteUrl: String? = null,
                    val eventUrl: String? = null) {
    fun toEvent(key: String): Event {
        return Event(key,
            category,
            shortDesc,
            longDesc,
            Date(eventDate),
            eventDuration,
            repeatType,
            timesRepeated,
            expired,
            siteUrl,
            eventUrl
            )
    }
}