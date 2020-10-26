package com.bugull.android.ext.coroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestChannel

suspend fun main() {
    val channel = Channel<Int>(Channel.UNLIMITED)
    val producer = GlobalScope.launch {
        var i = 0
        while (true) {
            delay(1000)
            println("send -> $i")
            channel.send(i++)
        }
    }

    val consume = GlobalScope.launch {
        while (true) {
            //delay(3000)
            val element = channel.receive()
            println("receiver -> $element")
        }
    }

    val consume1 = GlobalScope.launch {
        while (true) {
            delay(3000)
            val element = channel.iterator()
            while (element.hasNext()){
                val next = element.next()
                println("receiver -> $element")
            }

        }
    }
    producer.join()
    //consume.join()
    consume1.join()

}