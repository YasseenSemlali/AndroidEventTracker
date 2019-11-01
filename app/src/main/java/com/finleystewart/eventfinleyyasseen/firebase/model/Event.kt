package com.finleystewart.eventfinleyyasseen.firebase.model

import com.finleystewart.eventfinleyyasseen.firebase.EventRepeat
import com.google.firebase.database.Exclude
import java.lang.IllegalArgumentException
import java.util.*

class Event(
    val category: String,
    val shortDesc: String,
    val longDesc: String,
    val eventDate: Date = Calendar.getInstance().time,
    val eventDuration: Double = 1.0,
    val repeatType: Int = EventRepeat.SINGLE,
    val timesRepeated: Int = 1,
    val expired: Boolean = false,
    val siteUrl: String? = null,
    val eventUrl: String? = null,
    var key: String? = null
) {

    init {
        if(shortDesc.length > 20) {
            throw IllegalArgumentException("shortDesc is limited to 20 characters")
        }
        if(longDesc.length > 140) {
            throw IllegalArgumentException("shortDesc is limited to 140 characters")
        }

        if(!EventRepeat.isValid(repeatType)) {
            throw IllegalArgumentException("repeatType is invalid")
        }
        // TODO validate timesRepeated
    }

}