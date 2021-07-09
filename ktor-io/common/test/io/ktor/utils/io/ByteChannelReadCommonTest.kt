/*
 * Copyright 2014-2021 JetBrains s.r.o and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package io.ktor.utils.io

import io.ktor.test.dispatcher.*
import kotlin.test.*

class ByteChannelReadCommonTest {

    @Test
    fun testReadUtf8LineTo(): Unit = testSuspend {
        val channel = ByteChannel()
        channel.writeStringUtf8("Line 1\r\n")
        channel.writeStringUtf8("Line 2\r\n")
        channel.writeStringUtf8("Line 3\r\n")
        channel.flush()

        val builder = StringBuilder()

        channel.readUTF8LineTo(builder)
        println("Builder $builder")
        println(channel.availableForRead)
        channel.readUTF8LineTo(builder)
        println("Builder $builder")
        println(channel.availableForRead)
        channel.readUTF8LineTo(builder)
        println("Builder $builder")
        println(channel.availableForRead)
        println(channel.totalBytesRead)
    }
}
