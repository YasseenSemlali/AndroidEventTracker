package com.finleystewart.eventfinleyyasseen.firebase.model

import com.finleystewart.eventfinleyyasseen.firebase.EventRepeat
import java.util.*

class Event(
    val category: String = "",
    val shortDesc: String = "",
    val longDesc: String = "",
    val eventDate: Long = Calendar.getInstance().time.time,
    val eventDuration: Double = 1.0,
    val repeatType: EventRepeat = EventRepeat.SINGLE,
    val timesRepeated: Int = 1,
    val expired: Boolean = false,
    val siteUrl: String? = null,
    val eventUrl: String? = null
) {

    companion object  {
        val SHORT_DESC_MAX = 20;
        val LONG_DESC_MAX = 140;
        val MAX_DAYS = 60

        fun  timesRepeatedIsValid(
            repeatType: EventRepeat,
            timesRepeated: Int
        ): Boolean {
            // TODO validate timesRepeated

            return true;
        }
    }

    init {
        require(shortDesc.length <= SHORT_DESC_MAX) { "shortDesc is limited to $SHORT_DESC_MAX characters" }
        require(longDesc.length <= LONG_DESC_MAX) { "shortDesc is limited to $LONG_DESC_MAX characters" }
        require(timesRepeatedIsValid(repeatType, timesRepeated)) {"The event not repeat for more than $MAX_DAYS days after the start date"}

    }

    override fun toString(): String {
        return "Event(category='$category', shortDesc='$shortDesc', longDesc='$longDesc', eventDate=$eventDate, eventDuration=$eventDuration, repeatType=$repeatType, timesRepeated=$timesRepeated, expired=$expired, siteUrl=$siteUrl, eventUrl=$eventUrl)"
    }

}