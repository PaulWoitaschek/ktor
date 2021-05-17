/*
 * Copyright 2014-2021 JetBrains s.r.o and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package io.ktor.utils.io

import io.ktor.test.dispatcher.*
import kotlinx.coroutines.*
import kotlin.test.*

class ByteChannelCopyTest {

    @Test
    fun testFlushDuringCopyTo() = testSuspend {
        val source = ByteChannel()
        val destination = ByteChannel()

        launch(Dispatchers.Unconfined) {
            source.copyTo(destination)
        }

        source.writeByte(42)
        source.writeByte(42)
        source.flush()

        val actual = destination.readShort()
        assertEquals(10794, actual)
        source.close()
    }
}
