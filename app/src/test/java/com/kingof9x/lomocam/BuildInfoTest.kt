package com.kingof9x.lomocam

import org.junit.Assert.assertEquals
import org.junit.Test

class BuildInfoTest {
    @Test
    fun formatsVersionName() {
        assertEquals("v0.1.42", BuildInfo.formatVersion("0.1.42"))
    }

    @Test
    fun handlesMissingVersionName() {
        assertEquals("v?", BuildInfo.formatVersion(null))
        assertEquals("v?", BuildInfo.formatVersion(""))
    }
}
