package com.hank.guess

import java.util.*

class SecretNumber{
    val secretNumber = Random().nextInt(10)+1
    var count = 0

    fun validate(num : Int): Int {
        count ++
        return  num - secretNumber
    }
}

