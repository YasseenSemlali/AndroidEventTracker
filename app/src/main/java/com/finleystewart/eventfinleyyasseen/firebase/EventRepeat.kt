package com.finleystewart.eventfinleyyasseen.firebase

object EventRepeat {
    val SINGLE: Int = 0;
    val WEEKLY = 1;
    val MONTHLY = 2;

    fun isValid(repeat: Int) = repeat == 0 ||repeat == 1 ||repeat == 2
}