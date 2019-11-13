package com.finleystewart.eventfinleyyasseen.business


import java.io.Serializable
import java.util.*

class Event(
    val key: String,
    val category: String = "",
    val shortDesc: String = "",
    val longDesc: String = "",
    val eventDate: Date = Calendar.getInstance().time,
    val eventDuration: Double = 1.0,
    val repeatType: EventRepeat = EventRepeat.SINGLE,
    val timesRepeated: Int = 1,
    val expired: Boolean = false,
    val siteUrl: String? = null,
    val eventUrl: String? = null
) : Serializable {

    companion object  {
        val SHORT_DESC_MAX = 40
        val LONG_DESC_MAX = 140
    }

    init {
        require(shortDesc.length <= SHORT_DESC_MAX) { "shortDesc is limited to $SHORT_DESC_MAX characters. The length was: ${shortDesc.length}. The value was: $shortDesc" }
        require(longDesc.length <= LONG_DESC_MAX) { "longDesc is limited to $LONG_DESC_MAX characters. The length was: ${longDesc.length}. The value was: $longDesc" }

    }

    override fun toString(): String {
        return "Event(key='$key', category='$category', shortDesc='$shortDesc', longDesc='$longDesc', eventDate=$eventDate, eventDuration=$eventDuration, repeatType=$repeatType, timesRepeated=$timesRepeated, expired=$expired, siteUrl=$siteUrl, eventUrl=$eventUrl)"
    }

}